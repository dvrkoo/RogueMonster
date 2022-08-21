package com.mygdx.game.Maps;

import java.util.ArrayList;
import java.util.Arrays;

import com.mygdx.game.Utils.Enums.TILETYPE;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Island {

    public Tile centreTile;

    // ONE CHUNK
    public Chunk chunk;
    public ArrayList<Entity> entities = new ArrayList<Entity>();

    // TRACK CLICK
    int currentTileNo;
    int currentCol;
    int currentRow;

    // ISLAND MIN-MAX DIMENSION X-Y
    public Vector2 minMaxX = new Vector2();
    public Vector2 minMaxY = new Vector2();

    public static Array<Rectangle> collisionRectangle = new Array<Rectangle>();

    // Arrays for mapping code to texture
    String[] aGrassLeft = {
            "001001001", "001001001", "001001000", "000001001" };
    String[] aGrassRight = { "100100100", "100100000", "000100100" };
    String[] aGrassREnd = { "100000000" };
    String[] aGrassLEnd = { "001000000" };
    String[] aGrassTop = { "000000111", "000000011", "000000110" };
    String[] aGrassTopRight = { "000000100" };
    String[] aGrassTopLeft = { "000000001" };

    public Island() {
        entities.clear();
        setupTiles();
        codeTiles();
        addEntities();
        getCollisions();
    }

    public void getCollisions() {
        minMaxX.x = 100000000;
        minMaxX.y = 0;
        minMaxY.x = 100000000;
        minMaxY.y = 0;
        for (

        ArrayList<Tile> row : chunk.tiles) {
            for (Tile tile : row) {
                if (tile.isNotPassable() && tile.notIsAllWater()) {
                    // System.out.println(tile.details());
                    setMinMax(tile);
                    Rectangle rec = new Rectangle();
                    rec.x = tile.pos.x;
                    rec.y = tile.pos.y;
                    rec.height = tile.size;
                    rec.width = tile.size;
                    collisionRectangle.add(rec);
                }
                // else if (tile.pos)
            }
        }
        // System.out.println(collisions);
    }

    private void setupTiles() {
        chunk = new Chunk(33, 33, 40);

        int currentRow = 0;
        int rngW = MathUtils.random(5, 10);
        int rngH = MathUtils.random(5, 10);

        int centreTileRow = chunk.numberRows / 2;
        int centreTileCol = chunk.numberCols / 2;
        int firstTileRow = centreTileRow - (rngH);

        int maxRow = centreTileRow + rngH;
        int minRow = centreTileRow - rngH;
        int maxCol = centreTileCol + rngW;
        int minCol = centreTileCol - rngW;

        // CHUNK ROW
        ArrayList<Tile> chunkRow = new ArrayList<Tile>();

        // If number of tiles is needed.
        // int num_tiles = ((max_col - min_col)-1) * ((max_row - min_row)-1);

        for (int row = 0; row < chunk.numberRows; row++) {
            for (int col = 0; col < chunk.numberCols; col++) {
                // Create TILE
                Tile tile = new Tile(col, row, chunk.tileSize, TILETYPE.WATER, randomWater());

                // Make a small island
                if (row > minRow && row < maxRow && col > minCol && col < maxCol) {
                    tile.texture = randomGrass();
                    tile.type = TILETYPE.GRASS;

                    if (row == firstTileRow + 1) {
                        tile.texture = Media.cliff;
                        tile.type = TILETYPE.CLIFF;
                    } else {
                        // Chance to add trees etc
                    }
                }

                // ADD TILE TO CHUNK
                if (currentRow == row) {
                    // Add tile to current row
                    chunkRow.add(tile);

                    // Last row and column?
                    if (row == chunk.numberRows - 1 && col == chunk.numberCols - 1) {
                        chunk.tiles.add(chunkRow);
                    }
                } else {
                    // New row
                    currentRow = row;

                    // Add row to chunk
                    chunk.tiles.add(chunkRow);

                    // Clear chunk row
                    chunkRow = new ArrayList<Tile>();

                    // Add first tile to the new row
                    chunkRow.add(tile);
                }
            }
        }

        // Set centre tile for camera positioning
        centreTile = chunk.getTile(centreTileRow, centreTileCol);
    }

    private void updateImage(Tile tile) {
        // Secondary Texture is to add edges to tiles
        // TODO: Add array of textures per tile
        if (Arrays.asList(aGrassLeft).contains(tile.code)) {
            tile.secondaryTexture = Media.grassLeft;
        } else if (Arrays.asList(aGrassRight).contains(tile.code)) {
            tile.secondaryTexture = Media.grassRight;
        } else if (Arrays.asList(aGrassREnd).contains(tile.code)) {
            tile.secondaryTexture = Media.grassLeftUpperEdge;
        } else if (Arrays.asList(aGrassLEnd).contains(tile.code)) {
            tile.secondaryTexture = Media.grassRightUpperEdge;
        } else if (Arrays.asList(aGrassTop).contains(tile.code)) {
            tile.secondaryTexture = Media.grassTop;
        } else if (Arrays.asList(aGrassTopRight).contains(tile.code)) {
            tile.secondaryTexture = Media.grassTopRight;
        } else if (Arrays.asList(aGrassTopLeft).contains(tile.code)) {
            tile.secondaryTexture = Media.grassTopLeft;
        }
    }

    private Texture randomGrass() {
        Texture grass;

        int tile = MathUtils.random(20);
        switch (tile) {
            case 1:
                grass = Media.grass01;
                break;
            case 2:
                grass = Media.grass02;
                break;
            case 3:
                grass = Media.grass03;
                break;
            case 4:
                grass = Media.grass04;
                break;
            default:
                grass = Media.grass01;
                break;
        }

        return grass;
    }

    private Texture randomWater() {
        Texture water;

        int tile = MathUtils.random(20);
        switch (tile) {
            case 1:
                water = Media.water01;
                break;
            case 2:
                water = Media.water02;
                break;
            case 3:
                water = Media.water03;
                break;
            case 4:
                water = Media.water04;
                break;
            default:
                water = Media.water01;
                break;
        }

        return water;
    }

    private void codeTiles() {
        // Loop all tiles and set the initial code

        // 1 CHUNK ONLY ATM
        for (ArrayList<Tile> row : chunk.tiles) {
            for (Tile tile : row) {
                // Check all surrounding tiles and set 1 for pass 0 for non pass
                // 0 0 0
                // 0 X 0
                // 0 0 0

                int[] rows = { 1, 0, -1 };
                int[] cols = { -1, 0, 1 };

                for (int r : rows) {
                    for (int c : cols) {
                        tile.code += chunk.getTileCode(tile.row + r, tile.col + c);
                        updateImage(tile);
                    }
                }
            }
        }
    }

    private void addEntities() {
        // Loop all tiles and add random trees
        for (ArrayList<Tile> row : chunk.tiles) {
            for (Tile tile : row) {
                if (tile.isGrass()) {
                    if (MathUtils.random(100) > 90) {
                        tile.texture = Media.tree;
                        tile.size = 40;
                        tile.type = TILETYPE.TREE;
                    }
                }
            }
        }
    }

    private void setMinMax(Tile tile) {
        if (tile.pos.x < minMaxX.x)
            minMaxX.x = tile.pos.x;
        else if (tile.pos.x > minMaxX.y)
            minMaxX.y = tile.pos.x;
        if (tile.pos.y < minMaxY.x)
            minMaxY.x = tile.pos.y;
        else if (tile.pos.y > minMaxY.y)
            minMaxY.y = tile.pos.y;
    }
}
