package org.alfresco.repo.event.v1.model;

import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("AuditEntryResource")
public class AuditRecordResource implements Resource
{
    private String auditApplicationId;
    private Map<String, Object> auditData;

    public AuditRecordResource()
    {
        // No-args constructor for deserialization
    }

    public AuditRecordResource(String auditApplicationId, Map<String, Object> auditData)
    {
        this.auditApplicationId = auditApplicationId;
        this.auditData = auditData;
    }

    public Map<String, Object> getAuditData()
    {
        return auditData;
    }

    public void setAuditData(Map<String, Object> auditData)
    {
        this.auditData = auditData;
    }

    public String getAuditApplicationId()
    {
        return auditApplicationId;
    }

    public void setAuditApplicationId(String auditApplicationId)
    {
        this.auditApplicationId = auditApplicationId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AuditRecordResource that = (AuditRecordResource) o;
        return Objects.equals(auditApplicationId, that.auditApplicationId) &&
                Objects.equals(auditData, that.auditData);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(auditApplicationId, auditData);
    }
}
