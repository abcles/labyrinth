package abc.les.labyrinth.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LabyrinthService {

    /**
     * Enumeration for the directions in the matrix: up-down, left-right
     */
    enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    /**
     * Check if the matrix has the value true for the node (x,y), where x = row index and y = column index
     * Valid = true
     * @param matrix - input parameter of type matrix with boolean values
     * @param matrixNode - input parameter of type MatrixNode representing a node
     * @return true - the node has the value true OR false - the node isn't valid for the matrix or has the value false
     */
    private boolean pathOnNode(boolean[][] matrix, MatrixNode matrixNode) {
        try {
            if (matrix[matrixNode.getRow()][matrixNode.getColumn()] == true) {
                return true;
            }
        } catch (IndexOutOfBoundsException ex) {
            // do nothing
        }
        return false;
    }

    /**
     * Get the neighbour for a specified node and direction
     * @param direction - up-down, left-right
     * @param matrixNode - a node from the matrix
     * @return the neighbour node coordinates
     */
    private MatrixNode getNeighbour(Direction direction, MatrixNode matrixNode) {
        MatrixNode returnedMatrixNode = new MatrixNode(-1,-1);

        switch (direction) {
            case DOWN:
                returnedMatrixNode = new MatrixNode( matrixNode.getRow()+1, matrixNode.getColumn());
                break;
            case UP:
                returnedMatrixNode = new MatrixNode(matrixNode.getRow()-1, matrixNode.getColumn());
                break;
            case RIGHT:
                returnedMatrixNode = new MatrixNode(matrixNode.getRow(), matrixNode.getColumn()+1);
                break;
            case LEFT:
                returnedMatrixNode = new MatrixNode(matrixNode.getRow(), matrixNode.getColumn()-1);
                break;
        }
        return returnedMatrixNode;
    }

    /**
     * Search recursively for a path of true values in the matrix provided, starting from nodeStart and ending to
     * nodeEnd
     * @param matrix - boolean values matrix
     * @param visitedNodes - keep a list with all the nodes visited
     * @param blockedNodes - keep a list with all the nodes visited but not going to a valid path
     * @param nodeStart - MatrixNode for representing the starting indexes in the matrix
     * @param nodeEnd - MatrixNode for representing the ending indexes in the matrix
     * @return true if a valid path was found or false if a valid path wasn't found
     */
    private boolean findPathRecursively(boolean[][] matrix, List<MatrixNode> visitedNodes, List<MatrixNode> blockedNodes, MatrixNode nodeStart, MatrixNode nodeEnd) {
        MatrixNode nRight = getNeighbour(Direction.RIGHT, nodeStart);
        MatrixNode nLeft = getNeighbour(Direction.LEFT, nodeStart);
        MatrixNode nUp = getNeighbour(Direction.UP, nodeStart);
        MatrixNode nDown = getNeighbour(Direction.DOWN, nodeStart);

        MatrixNode nextNode = new MatrixNode(-1,-1);
        if (pathOnNode(matrix, nRight) && !visitedNodes.contains(nRight) && !blockedNodes.contains(nRight)) {
            nextNode = nRight;
        } else if (pathOnNode(matrix, nLeft) && !visitedNodes.contains(nLeft) && !blockedNodes.contains(nLeft)) {
            nextNode = nLeft;
        } else if (pathOnNode(matrix, nUp) && !visitedNodes.contains(nUp) && !blockedNodes.contains(nUp)) {
            nextNode = nUp;
        } else if (pathOnNode(matrix, nDown) && !visitedNodes.contains(nDown) && !blockedNodes.contains(nDown)) {
            nextNode = nDown;
        } else {
            // step back: remove the last node from visited nodes and add it to blocked nodes
            int index = visitedNodes.size() - 1;
            MatrixNode lastNode = visitedNodes.get(index);
            visitedNodes.remove(index);
            blockedNodes.add(lastNode);
            // Check if we have a valid node to start searching from
            if (index - 1 != 0) {
                // return the previous node as a next node to search from
                nextNode = visitedNodes.get(index-1);
            }
            else {
                return false;
            }
        }

        if (nextNode.equals(nodeEnd)) {
            System.out.println("Node considered: " + nextNode.toString() + ". Stop the search since a route has been found.");
            if (!visitedNodes.contains(nextNode)) {
                visitedNodes.add(nextNode);
            }
            return true;
        } else {
            if (!visitedNodes.contains(nextNode)) {
                visitedNodes.add(nextNode);
            }
            System.out.println("Node considered: " + nextNode.toString());
            return findPathRecursively(matrix, visitedNodes, blockedNodes, nextNode, nodeEnd);
        }
    }

    /**
     * Public function for searching a path of true values in a boolean matrix
     * @param matrix
     * @param fromRow
     * @param fromColumn
     * @param toRow
     * @param toColumn
     * @return a list of MatrixNode values if the path was found OR an empty list if no path was found
     */
    public List<MatrixNode> getPath(boolean[][] matrix, int fromRow, int fromColumn, int toRow, int toColumn) {
        List<MatrixNode> visitedNodes = new ArrayList<MatrixNode>();
        List<MatrixNode> blockedNodes = new ArrayList<MatrixNode>();
        MatrixNode nodeStart = new MatrixNode(fromRow, fromColumn);
        MatrixNode nodeEnd = new MatrixNode(toRow, toColumn);
        // add the initial node in the nodes visited
        visitedNodes.add(nodeStart);
        System.out.println("Node considered: " + nodeStart.toString() + " . This is the first node.");

        // Call the function to start searching a valid path
        boolean pathFound = findPathRecursively(matrix, visitedNodes, blockedNodes, nodeStart, nodeEnd);

        if (pathFound) {
            // Print the nodes from the path
            System.out.println("Nodes in the path: " + visitedNodes.toString());
            return visitedNodes;
        }

        return new ArrayList<MatrixNode>();
    }
}
