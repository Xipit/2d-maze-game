package com.maze.game.types;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.maze.game.Assets;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.MILLIS;

/**
 * <h1>Spieler Darstellung</h1>
 * Verantwortlich für alle Informationen zur Darstellung des Spielers.<br/>
 * Die tatsächliche Darstellung findet im LevelScreen statt.
 *
 * @author  Hanno Witzleb, Jörn Drechsler, Lilia Schneider
 */
public class PlayerRenderData {
    private final int playerWidth;
    private final int playerHeight;

    private Texture texture;
    private String textureName;
    public Sprite sprite;

    private LocalDateTime lastTextureChange = LocalDateTime.now();
    private int textureCycleIndex = 0;
    private final int[] textureIndexCycle = {1, 0, 1, 2};

    public PlayerRenderData(int playerWidth, int playerHeight){
        texture = Assets.manager.get(Assets.CAT_SITTING, Texture.class);

        this.playerWidth = playerWidth;
        this.playerHeight = playerHeight;
        this.sprite = new Sprite(texture, this.texture.getWidth(), this.texture.getHeight());
    }


    public void update(Vector2 currentDirectionVector, PlayerPosition playerPosition){
        final String textureName = determineTextureName(currentDirectionVector);
        final float textureRotation = determineTextureRotation(currentDirectionVector);

        if(!Objects.equals(this.textureName, textureName)){
            this.lastTextureChange = LocalDateTime.now();

            this.textureName = textureName;
            this.texture =  Assets.manager.get(textureName);
        }

        this.sprite.setTexture(this.texture);
        this.sprite.setRotation(textureRotation);
        this.sprite.setPosition(playerPosition.xMin - (30 - playerWidth), playerPosition.yMin - (30 - playerHeight));
    }

    private String determineTextureName(Vector2 currentMoveVector){
        if(currentMoveVector.x != 0 && currentMoveVector.y != 0){ //diagonal
            return Assets.CAT_DIAGONAL[getTextureIndex()];
        }
        else if(currentMoveVector.x == 0 ^ currentMoveVector.y == 0) { // XOR -> vertical
            return Assets.CAT_VERTICAL[getTextureIndex()];
        }
        else if(MILLIS.between(this.lastTextureChange, LocalDateTime.now()) > 200 + 150){ // only sit after 150ms of no input
            return Assets.CAT_SITTING;
        }

        return this.textureName;
    }

    private int getTextureIndex(){
        // walk-cycle
        if(MILLIS.between(this.lastTextureChange, LocalDateTime.now()) > 200){
            if(textureCycleIndex != textureIndexCycle.length - 1){
                textureCycleIndex++;
            }else{
                textureCycleIndex = 0;
            }
        }

        return textureIndexCycle[textureCycleIndex];
    }

    private float determineTextureRotation(Vector2 currentMoveVector){
        if(currentMoveVector.x != 0 && currentMoveVector.y != 0){ //diagonal
            if(currentMoveVector.x < 0 && currentMoveVector.y < 0)
                return 90F;
            else if(currentMoveVector.x > 0 && currentMoveVector.y < 0)
                return 180F;
            else if(currentMoveVector.x > 0 && currentMoveVector.y > 0)
                return 270F;
        }
        else if(currentMoveVector.x == 0 ^ currentMoveVector.y == 0) { // XOR -> vertical
            if(currentMoveVector.x < 0)
                return 90F;
            else if(currentMoveVector.y < 0)
                return 180F;
            else if(currentMoveVector.x > 0)
                return 270F;
        }

        return 0F;
    }

}
