package net.groupproject.quadtree;

import net.groupproject.util.Vector2d;

/**
 * @author Ansraer on 16.05.2018
 * @author Jakob Lang
 * @project TeamaufgabeProjekt
 */
public class QuadTreeNode<T> {

    private Vector2d pos;
    private T content;

    public Vector2d getPos() {
        return pos;
    }

    public T getContent() {
        return content;
    }

    public QuadTreeNode(Vector2d pos, T content){
        this.pos=pos;

        this.content=content;
    }
}
