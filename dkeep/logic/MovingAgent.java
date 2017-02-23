package dkeep.logic;

import dkeep.logic.Controller;

import java.awt.*;

public class MovingAgent {
	private Point agentCoords; // [x,y] current coordinates

	public Point getAgentCoords(){
		return agentCoords;
	}

	public void setInitialPos(int x, int y){
		agentCoords.x = x;
        agentCoords.y = y;
	}
	
	
	
	
	/*public boolean move(char[][] board,char keyPressed, int currentLevel){

		int tempAgentXCoord;
		int tempAgentYCoord;
		if (keyPressed == 'w' || keyPressed == 'W'){
			tempAgentXCoord = agentCoords[0];
			tempAgentYCoord = agentCoords[1]- 1;
		}
		else if (keyPressed == 'a' || keyPressed == 'A'){
			tempAgentXCoord = agentCoords[0] -1;
			tempAgentYCoord = agentCoords[1];
		}
		else if (keyPressed == 's' || keyPressed == 'S'){
			tempAgentXCoord = agentCoords[0];
			tempAgentYCoord = agentCoords[1] + 1;
		}
		else if (keyPressed == 'd' || keyPressed == 'D'){
			tempAgentXCoord = agentCoords[0] +1;
			tempAgentYCoord = agentCoords[1];
		}
		else {
			return false;
		}

		agentPrevCoords[0] = agentCoords[0];
		agentPrevCoords[1] = agentCoords[1];
		agentCoords[0] = tempAgentXCoord;
		agentCoords[1] = tempAgentYCoord;

		if (board[agentCoords[1]][agentCoords[0]] == 'k' && agentSignature == 'H' && currentLevel == 1){
			Controller.changeAllDoorsToStairs(board);
		}
		else if (board[agentCoords[1]][agentCoords[0]] == 'k' && agentSignature == 'H' && currentLevel == 2){
			agentSignature = 'K';
		}
		else if (agentSignature == 'O' && board[agentCoords[1]][agentCoords[0]] == 'k'){
			agentSignature = '$';
		}
		else if (agentSignature == '$' && board[agentCoords[1]][agentCoords[0]] == ' '){
			board[agentPrevCoords[1]][agentPrevCoords[0]] = 'k';
			agentSignature = 'O';
		}
		else if (board[agentCoords[1]][agentCoords[0]] == 'X' ||(board[agentCoords[1]][agentCoords[0]] == 'I' && agentSignature != 'K' ) ){
			agentCoords[1] = agentPrevCoords[1];
			agentCoords[0] = agentPrevCoords[0];
		}
		else if (board[agentCoords[1]][agentCoords[0]] == 'I' && agentSignature == 'K'){
			agentCoords[1] = agentPrevCoords[1];
			agentCoords[0] = agentPrevCoords[0];
			Controller.changeAllDoorsToStairs(board);
		}
	return true;
	}
	*/
}
