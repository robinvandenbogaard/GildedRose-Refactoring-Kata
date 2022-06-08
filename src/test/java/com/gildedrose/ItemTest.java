package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class ItemTest
{

    @Test
    void testToString()
    {
        Item item = new Item("Potion", 1, 10);

        assertThat(item.toString(), is("Potion, 1, 10"));
    }
}
