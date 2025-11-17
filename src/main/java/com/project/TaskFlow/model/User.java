package com.project.TaskFlow.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CompanyMembership> memberships = new HashSet<>();

    @OneToMany(mappedBy = "manager")
    private Set<Project> managingProjects = new HashSet<>();

    @ManyToMany(mappedBy = "members")
    private Set<Project> assignedProjects = new HashSet<>();

    @OneToMany(mappedBy = "assignedTo")
    private Set<Task> assignedTasks = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<TaskComment> comments = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskActivity> activities = new HashSet<>();

    @OneToMany(mappedBy = "uploadedBy", cascade = CascadeType.ALL)
    private Set<Attachment> uploadedAttachments = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notification> notifications = new HashSet<>();

    public User() {
    }

    public User(Long id, String name, String email, String password, Role role, LocalDateTime createdAt, LocalDateTime updatedAt, Set<CompanyMembership> memberships, Set<Project> managingProjects, Set<Project> assignedProjects, Set<Task> assignedTasks, Set<TaskComment> comments, Set<TaskActivity> activities, Set<Attachment> uploadedAttachments, Set<Notification> notifications) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.memberships = memberships;
        this.managingProjects = managingProjects;
        this.assignedProjects = assignedProjects;
        this.assignedTasks = assignedTasks;
        this.comments = comments;
        this.activities = activities;
        this.uploadedAttachments = uploadedAttachments;
        this.notifications = notifications;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<CompanyMembership> getMemberships() {
        return memberships;
    }

    public void setMemberships(Set<CompanyMembership> memberships) {
        this.memberships = memberships;
    }

    public Set<Project> getManagingProjects() {
        return managingProjects;
    }

    public void setManagingProjects(Set<Project> managingProjects) {
        this.managingProjects = managingProjects;
    }

    public Set<Project> getAssignedProjects() {
        return assignedProjects;
    }

    public void setAssignedProjects(Set<Project> assignedProjects) {
        this.assignedProjects = assignedProjects;
    }

    public Set<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(Set<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public Set<TaskComment> getComments() {
        return comments;
    }

    public void setComments(Set<TaskComment> comments) {
        this.comments = comments;
    }

    public Set<TaskActivity> getActivities() {
        return activities;
    }

    public void setActivities(Set<TaskActivity> activities) {
        this.activities = activities;
    }

    public Set<Attachment> getUploadedAttachments() {
        return uploadedAttachments;
    }

    public void setUploadedAttachments(Set<Attachment> uploadedAttachments) {
        this.uploadedAttachments = uploadedAttachments;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }
}
