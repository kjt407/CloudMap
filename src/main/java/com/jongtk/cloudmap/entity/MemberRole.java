package com.example.studyguide.entity;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

public enum MemberRole{
    USER, MANAGER, ADMIN
}

