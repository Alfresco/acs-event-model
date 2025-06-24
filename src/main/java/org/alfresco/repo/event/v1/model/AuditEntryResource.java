package org.alfresco.repo.event.v1.model;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.ZonedDateTime;
import java.util.Map;



public class AuditEntryResource implements Resource
{
    private final String auditedActionType;
    private final ZonedDateTime createdAt;
    private final UserInfo createdByUser;
    private final Map<String, ?> auditData;

    private AuditEntryResource(Builder builder)
    {
        this.auditedActionType = builder.auditedActionType;
        this.createdAt = builder.createdAt;
        this.createdByUser = builder.createdByUser;
        this.auditData = builder.auditData;
    }

    public static Builder builder()
    {
        return new Builder();
    }


    public String getAuditedActionType()
    {
        return auditedActionType;
    }

    public ZonedDateTime getCreatedAt()
    {
        return createdAt;
    }

    public UserInfo getCreatedByUser()
    {
        return createdByUser;
    }

    public Map<String, ?> getAuditData()
    {
        return auditData;
    }


    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String auditedActionType;
        private ZonedDateTime createdAt;
        private UserInfo createdByUser;
        private Map<String, ?> auditData;

        public Builder setAuditedActionType(String auditedActionType)
        {
            this.auditedActionType = auditedActionType;
            return this;
        }

        public Builder setCreatedAt(ZonedDateTime createdAt)
        {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setCreatedByUser(UserInfo createdByUser)
        {
            this.createdByUser = createdByUser;
            return this;
        }

        public Builder setAuditData(Map<String, ?> auditData)
        {
            this.auditData = auditData;
            return this;
        }

        public AuditEntryResource build() {
            return new AuditEntryResource(this);
        }
    }
}
