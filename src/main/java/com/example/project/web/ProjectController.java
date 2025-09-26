package com.example.project.web;

import com.example.project.entity.Attachment;
import com.example.project.entity.Project;
import com.example.project.service.ProjectService;
import com.example.project.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
  private final ProjectService projectService;
  private final S3Service s3Service;

  public ProjectController(ProjectService projectService, S3Service s3Service) {
    this.projectService = projectService;
    this.s3Service = s3Service;
  }

  @GetMapping
  public List<Project> list() { return projectService.list(); }

  @GetMapping("{id}")
  public ResponseEntity<Project> get(@PathVariable Long id) {
    return projectService.find(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Project> create(@RequestBody Project project) {
    Project saved = projectService.create(project);
    return ResponseEntity.ok(saved);
  }

  @PostMapping("{id}/upload")
  public ResponseEntity<?> upload(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
    String key = "projects/" + id + "/" + UUID.randomUUID() + "-" + Objects.requireNonNull(file.getOriginalFilename());
    s3Service.upload(key, file.getInputStream(), file.getSize(), file.getContentType());
    Attachment att = new Attachment();
    att.setFilename(file.getOriginalFilename());
    att.setS3Key(key);
    projectService.addAttachment(id, att);
    return ResponseEntity.ok(Map.of("s3Key", key));
  }
}
