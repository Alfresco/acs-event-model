package org.alfresco.repo.event.v1.model;

import java.io.Serializable;
import java.util.Map;

public class AuditEntryResource implements Resource
{
    private String entryType;
    private Map<String, Serializable> entryData;

    public AuditEntryResource()
    {}

    public AuditEntryResource(String entryType, Map<String, Serializable> entryData)
    {
        this.entryType = entryType;
        this.entryData = entryData;
    }

    public Map<String, Serializable> getEntryData()
    {
        return entryData;
    }

    public String getEntryType()
    {
        return entryType;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;

        AuditEntryResource that = (AuditEntryResource) o;
        return entryType.equals(that.entryType) && entryData.equals(that.entryData);
    }

    @Override
    public int hashCode()
    {
        int result = entryType.hashCode();
        result = 31 * result + entryData.hashCode();
        return result;
    }
}
