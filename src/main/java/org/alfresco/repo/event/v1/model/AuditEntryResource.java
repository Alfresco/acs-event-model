package org.alfresco.repo.event.v1.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class AuditEntryResource implements Resource
{
    private String entryType;
    private Long entryDBId;
    private String username;
    private Map<String, Serializable> entryData;

    public AuditEntryResource()
    {}

    public AuditEntryResource(String entryType, Map<String, Serializable> entryData, String username, Long entryDBId)
    {
        this.entryType = entryType;
        this.entryData = entryData;
        this.username = username;
        this.entryDBId = entryDBId;
    }

    public Map<String, Serializable> getEntryData()
    {
        return entryData;
    }

    public String getEntryType()
    {
        return entryType;
    }

    public String getUsername()
    {
        return username;
    }

    public Long getEntryDBId()
    {
        return entryDBId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;

        AuditEntryResource that = (AuditEntryResource) o;
        return Objects.equals(entryType, that.entryType) && Objects.equals(entryDBId, that.entryDBId) && Objects.equals(username, that.username) && Objects.equals(entryData, that.entryData);
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hashCode(entryType);
        result = 31 * result + Objects.hashCode(entryDBId);
        result = 31 * result + Objects.hashCode(username);
        result = 31 * result + Objects.hashCode(entryData);
        return result;
    }
}
