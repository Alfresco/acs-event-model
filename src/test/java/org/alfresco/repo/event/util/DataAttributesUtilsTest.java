package org.alfresco.repo.event.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.alfresco.repo.event.util.DataAttributesUtils.containsNotBlankContent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.alfresco.repo.event.v1.model.ContentInfo;
import org.alfresco.repo.event.v1.model.DataAttributes;
import org.alfresco.repo.event.v1.model.NodeResource;
import org.alfresco.repo.event.v1.model.Resource;

@ExtendWith(MockitoExtension.class)
class DataAttributesUtilsTest
{
    @Mock
    private DataAttributes<Resource> dataAttributes;
    @Mock
    private NodeResource nodeResource;
    @Mock
    private ContentInfo contentInfo;

    @BeforeEach
    void setUp()
    {
        lenient().when(dataAttributes.getResource()).thenReturn(nodeResource);
        lenient().when(nodeResource.getContent()).thenReturn(contentInfo);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 100L, Long.MAX_VALUE})
    void testContainsNotBlankContent_contentSizeGreaterThanZero(Long contentSize)
    {
        // given
        when(contentInfo.getSizeInBytes()).thenReturn(contentSize);

        // when and then
        assertThat(containsNotBlankContent(dataAttributes)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -100L, Long.MIN_VALUE})
    void testContainsNotBlankContent_contentSizeNotGreaterThanZero(Long contentSize)
    {
        // given
        when(contentInfo.getSizeInBytes()).thenReturn(contentSize);

        // when and then
        assertThat(containsNotBlankContent(dataAttributes)).isFalse();
    }

    @Test
    void testContainsNotBlankContent_nullContentSize()
    {
        // given
        when(nodeResource.getContent()).thenReturn(null);

        // when and then
        assertThat(containsNotBlankContent(dataAttributes)).isFalse();
    }

    @Test
    void testContainsNotBlankContent_resourceNull()
    {
        // given
        when(dataAttributes.getResource()).thenReturn(null);

        // when and then
        assertThat(containsNotBlankContent(dataAttributes)).isFalse();
    }

    @Test
    void testContainsNotBlankContent_dataAttributesNull()
    {
        // when and then
        assertThat(containsNotBlankContent(null)).isFalse();
    }

    @Test
    void testContainsNotBlankContent_notNodeResource()
    {
        // given
        when(dataAttributes.getResource()).thenReturn(mock(Resource.class));

        // when and then
        assertThat(containsNotBlankContent(dataAttributes)).isFalse();
    }
}
