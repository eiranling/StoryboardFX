package com.eiranling.components;

import org.junit.Assert;
import org.junit.Test;

public class StoryTest {

    @Test
    public void setTitleFromCreation() {
        Assert.assertEquals("Test1", new Story<>("Test1").getTitle());
    }

    @Test
    public void setTitleUpdatesTextProperty() {
        Story story = new Story();
        story.setTitle("Test2");
        Assert.assertEquals("Test2", story.titleTextProperty().getValue());
    }
}
