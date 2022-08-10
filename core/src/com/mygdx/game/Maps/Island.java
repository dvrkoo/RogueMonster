package com.mygdx.game.Maps;

import java.util.ArrayList;
import java.util.Arrays;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.Maps.Media;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Maps.Enums.TILETYPE;
import com.mygdx.game.Maps.Entity;

public class Island {


    Tile centre_tile;
    Tile clicked_tile;

    // CHUNKS TODO: Add multiple chunks
    // public Map<Integer, ArrayList<Chunk> chunks = new Map<Integer,
    // ArrayList<Chunk>();

    // ONE CHUNK
    public Chunk chunk;
    ArrayList<Entity> entities = new ArrayList<Entity>();

    // TRACK CLICK
    int current_tile_no;
    int current_col;
    int current_row;

    // Arrays for mapping code to texture
    String[] a_grass_left = { "001001001", "001001001", "001001000", "000001001" };
    String[] a_grass_right = { "100100100", "100100000", "000100100" };
    String[] a_grass_r_end = { "100000000" };
    String[] a_grass_l_end = { "001000000" };
    String[] a_grass_top = { "000000111", "000000011", "000000110" };
    String[] a_grass_top_right = { "000000100" };
    String[] a_grass_top_left = { "000000001" };

    public Island() {
        setup_tiles();
        code_tiles();
    }

    private void setup_tiles() {
        chunk = new Chunk(33, 33, 30);

        int current_row = 0;
        int rng_w = MathUtils.random(5, 18);
        int rng_h = MathUtils.random(5, 18);

        int centre_tile_row = chunk.number_rows / 2;
        int centre_tile_col = chunk.number_cols / 2;
        int first_tile_row = centre_tile_row - (rng_h);

        int max_row = centre_tile_row + rng_h;
        int min_row = centre_tile_row - rng_h;
        int max_col = centre_tile_col + rng_w;
        int min_col = centre_tile_col - rng_w;

        // CHUNK ROW
        ArrayList<Tile> chunk_row = new ArrayList<Tile>();

        // If number of tiles is needed.
        // int num_tiles = ((max_col - min_col)-1) * ((max_row - min_row)-1);

        for (int row = 0; row < chunk.number_rows; row++) {
            for (int col = 0; col < chunk.number_cols; col++) {
                // Create TILE
                Tile tile = new Tile(col, row, chunk.tile_size, TILETYPE.WATER, random_water());

                // Make a small island
                if (row > min_row && row < max_row && col > min_col && col < max_col) {
                    tile.texture = random_grass();
                    tile.type = TILETYPE.GRASS;

                    if (row == first_tile_row + 1) {
                        tile.texture = Media.cliff;
                        tile.type = TILETYPE.CLIFF;
                    } else {
                        // Chance to add trees etc
                    }
                }

                // ADD TILE TO CHUNK
                if (current_row == row) {
                    // Add tile to current row
                    chunk_row.add(tile);

                    // Last row and column?
                    if (row == chunk.number_rows - 1 && col == chunk.number_cols - 1) {
                        chunk.tiles.add(chunk_row);
                    }
                } else {
                    // New row
                    current_row = row;

                    // Add row to chunk
                    chunk.tiles.add(chunk_row);

                    // Clear chunk row
                    chunk_row = new ArrayList<Tile>();

                    // Add first tile to the new row
                    chunk_row.add(tile);
                }
            }
        }

        // Set centre tile for camera positioning
        centre_tile = chunk.get_tile(centre_tile_row, centre_tile_col);
    }

    private void update_image(Tile tile) {
        // Secondary Texture is to add edges to tiles
        // TODO: Add array of textures per tile
        if (Arrays.asList(a_grass_left).contains(tile.code)) {
            tile.secondary_texture = Media.grass_left;
        } else if (Arrays.asList(a_grass_right).contains(tile.code)) {
            tile.secondary_texture = Media.grass_right;
        } else if (Arrays.asList(a_grass_r_end).contains(tile.code)) {
            tile.secondary_texture = Media.grass_left_upper_edge;
        } else if (Arrays.asList(a_grass_l_end).contains(tile.code)) {
            tile.secondary_texture = Media.grass_right_upper_edge;
        } else if (Arrays.asList(a_grass_top).contains(tile.code)) {
            tile.secondary_texture = Media.grass_top;
        } else if (Arrays.asList(a_grass_top_right).contains(tile.code)) {
            tile.secondary_texture = Media.grass_top_right;
        } else if (Arrays.asList(a_grass_top_left).contains(tile.code)) {
            tile.secondary_texture = Media.grass_top_left;
        }
    }

    private Texture random_grass() {
        Texture grass;

        int tile = MathUtils.random(20);
        switch (tile) {
            case 1:
                grass = Media.grass_01;
                break;
            case 2:
                grass = Media.grass_02;
                break;
            case 3:
                grass = Media.grass_03;
                break;
            case 4:
                grass = Media.grass_04;
                break;
            default:
                grass = Media.grass_01;
                break;
        }

        return grass;
    }

    private Texture random_water() {
        Texture water;

        int tile = MathUtils.random(20);
        switch (tile) {
            case 1:
                water = Media.water_01;
                break;
            case 2:
                water = Media.water_02;
                break;
            case 3:
                water = Media.water_03;
                break;
            case 4:
                water = Media.water_04;
                break;
            default:
                water = Media.water_01;
                break;
        }

        return water;
    }

    private void code_tiles() {
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
                        tile.code += chunk.get_tile_code(tile.row + r, tile.col + c);
                        update_image(tile);
                    }
                }
            }
        }
    }
}