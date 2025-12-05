<recipetype:mysticalagriculture:awakening>.addRecipe("netherite_from_dirt_2", <item:minecraft:netherite_ingot>, <item:minecraft:diamond>.transformReplace(<item:minecraft:apple>), [
    <item:minecraft:dirt>.reuse(), <item:minecraft:dirt>.reuse(), <item:minecraft:dirt>, <item:minecraft:dirt>.reuse()
], [
    <item:mysticalagriculture:air_essence> * 10, <item:mysticalagriculture:earth_essence> * 20, <item:mysticalagriculture:water_essence> * 30, <item:mysticalagriculture:fire_essence> * 40
]);

<recipetype:mysticalagriculture:awakening>.addRecipe("custom_essence_vessel_color", <item:minecraft:gold_ingot>, <item:minecraft:emerald>, [
    <item:minecraft:sand>, <item:minecraft:sand>, <item:minecraft:sand>, <item:minecraft:sand>.reuse()
], [
    <item:mysticalagriculture:diamond_essence> * 10, <item:mysticalagriculture:earth_essence> * 20, <item:mysticalagriculture:water_essence> * 30, <item:mysticalagriculture:fire_essence> * 40
]);

//<recipetype:mysticalagriculture:awakening>.remove(<item:mysticalagriculture:awakened_supremium_block>);

<recipetype:mysticalagriculture:awakening>.setEssenceVesselColor(<item:mysticalagriculture:diamond_essence>, "#89ddf7");
//<recipetype:mysticalagriculture:awakening>.setEssenceVesselColor(<item:mysticalagriculture:water_essence>, "#BBBBBB");

var recipes = <recipetype:mysticalagriculture:awakening>.allRecipes;

println("There are " + recipes.length + " awakening recipes");