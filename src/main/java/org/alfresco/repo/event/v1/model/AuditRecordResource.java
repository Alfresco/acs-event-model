package org.alfresco.repo.event.v1.model;

public abstract class AuditRecordResource implements Resource
{
    private String auditApplicationId;
    private String auditData;

    public AuditRecordResource()
    {

    }

    public AuditRecordResource(String auditApplicationId, String auditData)
    {
        this.auditApplicationId = auditApplicationId;
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

    public String getAuditData()
    {
        return auditData;
    }

    public void setAuditData(String auditData)
    {
        this.auditData = auditData;
    }
}
