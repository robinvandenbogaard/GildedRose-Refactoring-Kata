package com.gildedrose;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class GildedRoseTest
{

    private Item[] items;
    private GildedRose app;

    private Item foo;
    private Item agedBrie;
    private Item sulfuras;
    private Item backstagePass;

    @BeforeEach
    void setup() {
        foo = new Item("foo", 0, 0);
        agedBrie = new Item("Aged Brie", 0, 0);
        sulfuras = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
        backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 0);
        items = new Item[] {foo, agedBrie, sulfuras, backstagePass };
        app = new GildedRose(items);
    }

    @Test @DisplayName("The Quality of an item degrades by one")
    void itemsDegrade()
    {
        foo.quality = 1;
        app.updateQuality();
        assertThat(foo.quality, is(0));
    }

    @Test @DisplayName("The Quality of an item degrades by one for every step")
    void itemsDegradesGraduatly()
    {
        foo.quality = 5;
        updateQualityNTimes(5);
        assertThat(foo.quality, is(0));
    }

    private void updateQualityNTimes(int days)
    {
        for (int i = 0; i < days; i++)
        {
            app.updateQuality();
        }
    }

    @Test @DisplayName("Once the sell by date has passed, Quality degrades twice as fast")
    void reduceFast()
    {
        foo.quality = 4;
        app.updateQuality();
        assertThat(foo.quality, is(2));
    }

    @Test @DisplayName("The Quality of an item is never negative")
    void neverNegative()
    {
        foo.quality = 0;
        app.updateQuality();
        assertThat(foo.quality, is(0));
    }

    @Test @DisplayName("\"Aged Brie\" actually increases in Quality the older it gets")
    void agedBrie()
    {
        agedBrie.sellIn = 1;
        app.updateQuality();
        assertThat(agedBrie.quality, is(1));
    }

    @Test @DisplayName("The Quality of an item is never more than 50")
    void maxQualityIsFifty()
    {
        agedBrie.quality = 50;
        app.updateQuality();
        assertThat(agedBrie.quality, is(50));
    }

    @Test
    @DisplayName("\"Sulfuras\", being a legendary item, never decreases in Quality")
    void sulfarasNeverDecreases()
    {
        sulfuras.quality = 80;
        app.updateQuality();
        assertThat(sulfuras.quality, is(80));
    }

    @Test
    @DisplayName("\"Sulfuras\", being a legendary item, never has to be sold")
    void sulfarasNeverToBeSold()
    {
        sulfuras.sellIn = 80;
        app.updateQuality();
        assertThat(sulfuras.sellIn, is(80));
    }

    @Test @DisplayName("\"Backstage passes\", increases in Quality")
    void backstagePass()
    {
        backstagePass.sellIn = 11;
        backstagePass.quality = 1;
        app.updateQuality();
        assertThat(backstagePass.quality, is(2));
    }

    @Test @DisplayName("\"Backstage passes\", increases by 2 when there are 10 days left")
    void backstagePassSoon()
    {
        backstagePass.sellIn = 10;
        backstagePass.quality = 2;
        app.updateQuality();
        assertThat(backstagePass.quality, is(4));
    }

    @Test @DisplayName("\"Backstage passes\", increases by 3 when there are 5 days left")
    void backstagePassHot()
    {
        backstagePass.sellIn = 5;
        backstagePass.quality = 2;
        app.updateQuality();
        assertThat(backstagePass.quality, is(5));
    }
}
