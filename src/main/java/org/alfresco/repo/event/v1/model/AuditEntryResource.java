package org.alfresco.repo.event.v1.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class AuditEntryResource implements Resource
{
    private String entryType;
    private String username;
    private Long entryDBId;
    private Map<String, Serializable> entryData;

    public AuditEntryResource()
    {}

    public AuditEntryResource(String entryType, String username, Long entryDBId, Map<String, Serializable> entryData)
    {
        this.entryType = entryType;
        this.username = username;
        this.entryDBId = entryDBId;
        this.entryData = entryData;
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

    public Map<String, Serializable> getEntryData()
    {
        return entryData;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;

        AuditEntryResource that = (AuditEntryResource) o;
        return Objects.equals(entryType, that.entryType) && Objects.equals(username, that.username) && Objects.equals(entryDBId, that.entryDBId) && Objects.equals(entryData, that.entryData);
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hashCode(entryType);
        result = 31 * result + Objects.hashCode(username);
        result = 31 * result + Objects.hashCode(entryDBId);
        result = 31 * result + Objects.hashCode(entryData);
        return result;
    }
}
