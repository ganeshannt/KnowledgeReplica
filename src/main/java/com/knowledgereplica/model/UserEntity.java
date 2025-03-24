package com.knowledgereplica.model;

import com.knowledgereplica.constant.Provider;
import com.knowledgereplica.constant.Role;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "users")
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String firstName;
  private String lastName;

  @Column(unique = true)
  private String email;

  private String password;
  private boolean verified;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Enumerated(EnumType.STRING)
  private Provider provider;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private Set<TokenEntity> tokens;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private Set<PostEntity> posts;

  @Column(updatable = false)
  @CreationTimestamp
  private Timestamp createdAt;

  @UpdateTimestamp private Timestamp updatedAt;

  public UserEntity() {}

  public UserEntity(long id, String firstName, String lastName, String email, String password) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.role = Role.USER;
    this.provider = Provider.FORM;
    this.verified = true;
  }

  public long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
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

  public boolean isVerified() {
    return verified;
  }

  public void setVerified(boolean verified) {
    this.verified = verified;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Provider getProvider() {
    return provider;
  }

  public void setProvider(Provider provider) {
    this.provider = provider;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Set<TokenEntity> getTokens() {
    return tokens;
  }

  public void setTokens(Set<TokenEntity> tokens) {
    this.tokens = tokens;
  }

  public Set<PostEntity> getPosts() {
    return posts;
  }

  public void setPosts(Set<PostEntity> posts) {
    this.posts = posts;
  }

  public LocalDate getCreationDate() {
    Timestamp date = this.createdAt;
    return date.toLocalDateTime().toLocalDate();
  }

  public LocalDate getUpdationDate() {
    Timestamp date = this.updatedAt;
    return date.toLocalDateTime().toLocalDate();
  }
}
