package com.example.project.service;

import com.example.project.entity.Attachment;
import com.example.project.entity.Project;
import com.example.project.repository.AttachmentRepository;
import com.example.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
  private final ProjectRepository projectRepo;
  private final AttachmentRepository attachmentRepo;

  public ProjectService(ProjectRepository projectRepo, AttachmentRepository attachmentRepo) {
    this.projectRepo = projectRepo;
    this.attachmentRepo = attachmentRepo;
  }

  public Project create(Project p) { return projectRepo.save(p); }
  public List<Project> list() { return projectRepo.findAll(); }
  public Optional<Project> find(Long id) { return projectRepo.findById(id); }

  @Transactional
  public Attachment addAttachment(Long projectId, Attachment attach) {
    Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
    attach.setProject(project);
    project.getAttachments().add(attach);
    return attachmentRepo.save(attach);
  }

  public void delete(Long id) { projectRepo.deleteById(id); }
}
