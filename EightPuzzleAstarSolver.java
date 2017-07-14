package eightPuzzle;
import java.util.*;
import java.util.Map.Entry;
/*
 * EightPuzzle is played on a 3-by-3 grid with 8 square blocks labeled 1 through 8 and a blank square represented
 * as zero in this game. The goal is to rearrange the blocks so that they are in order. 
 * it is permitted to slide blocks horizontally or vertically into the blank square which is represented as zero
 * 
 * Problem: Find a path from a start state to a goal state using "A Star" Search (A*) with Manhattan distance heuristics
 *        
 * Ex:
 *  Start State (Initial Board): 
			 					1 2 3
 *                               4 5 6
 *                               0 7 8
 *                               
 *  End State (Goal State):      
			 					1 2 3
 *                               4 5 6
 *                               7 8 0
 *                               
 * Solution:
 * 1 2 3        1 2 3        1 2 3
 * 4 5 6  ==> 	4 5 6  ==> 	4 5 6
 * 0 7 8         7 0 8       7 8 0   
 * 
 *  Author: Viswanath Kasireddy                  
 */

public class EightPuzzleAstarSolver {
	public String GoalStateBoard = "123456780";
	int BranchPosition ;
	int SizeLCS;
	List<String> LeastCostSuccessorsN ;
	String LeastCostSuccessorN;
	Queue<String> queue = new LinkedList<String>();
	Map<String,Integer> treeDepth = new LinkedHashMap<String, Integer>(); // to avoid repeated nodes
	Map<String,String> stateLog = new LinkedHashMap<String,String>();
	Map<String,Integer> heuristic = new LinkedHashMap<String,Integer>();
	
	void addState(String newBoard, String oldBoard){
		if (oldBoard == null){
    		   BranchPosition = 0;
    		   treeDepth.put(newBoard, BranchPosition);
    		   stateLog.put(newBoard, oldBoard);
     	}
		queue.add(newBoard);
	}
	
	String findLeastCostBoard (String CurrentBoard) {
		processSuccessors(CurrentBoard);
	    LeastCostSuccessorsN = getMultipleleastCostSucessors();
	    SizeLCS = LeastCostSuccessorsN.size();
	    if (SizeLCS == 1){
	        LeastCostSuccessorN = LeastCostSuccessorsN.get(0);
	        return LeastCostSuccessorN;
	    }else{
	    	     //obtain least cost successor with recursive calculation if more than one successor has same Manhattan distance at any given branch
	         recursiveCalc () ;
	    }
	    return LeastCostSuccessorN;
	    }
	String up (String CurrentBoard){
        String ChangedBoard = "null";
		   int spacePos = CurrentBoard.indexOf("0");
		   if(spacePos>2){
		     ChangedBoard = 
			 CurrentBoard.substring(0,spacePos-3)+"0"+CurrentBoard.substring(spacePos-2,spacePos)+
			 CurrentBoard.charAt(spacePos-3)+
			 CurrentBoard.substring(spacePos+1);
		   }
		return ChangedBoard;
	}
	String down(String CurrentBoard){
	    String ChangedBoard = "null";
		int spacePos = CurrentBoard.indexOf("0");
		if(spacePos<6){
          ChangedBoard = 
	      CurrentBoard.substring(0,spacePos)+CurrentBoard.substring(spacePos+3,spacePos+4)+
	      CurrentBoard.substring(spacePos+1,spacePos+3)+"0"+
          CurrentBoard.substring(spacePos+4);
        }
		return ChangedBoard;
	}
	String left(String CurrentBoard){
		String ChangedBoard = "null";
	     int spacePos = CurrentBoard.indexOf("0");
    	 if (spacePos!=0 && spacePos!=3 && spacePos!=6){
	         ChangedBoard = CurrentBoard.substring(0,spacePos-1)+"0"+CurrentBoard.charAt(spacePos-1)+
	        		CurrentBoard.substring(spacePos+1);
	     }
    	 return ChangedBoard;
	}
	String right(String CurrentBoard){
		 String ChangedBoard = "null";
		     int spacePos = CurrentBoard.indexOf("0");
	     if (spacePos!=2 && spacePos!=5 && spacePos!=8){
	        ChangedBoard = CurrentBoard.substring(0,spacePos)+CurrentBoard.charAt(spacePos+1)+"0"+
	        CurrentBoard.substring(spacePos+2);
	     }
	     return ChangedBoard;
	}
	 
	void heuristicCalc (String Successor, String CurrentBoard){
		if (Successor != "null"){
		   int manhattanDistance = manhattanCalc (Successor);
		   int currentBranch = treeDepth.get(CurrentBoard);
		   int levelCost = currentBranch + 1;
		   int gCost = manhattanDistance + levelCost;
		   //avoid already searched nodes
		   if (!treeDepth.containsKey(Successor)){
			   heuristic.put(Successor, gCost);
			   stateLog.put(Successor, CurrentBoard);
			   treeDepth.put(Successor, levelCost);
		   }
		   check(Successor);
		}
	}
	int manhattanCalc(String Board) {
		int [][] tiles = stringToDoubleArray (Board) ;
		int manhattan=0;
		for (int x = 0; x < 3; x++){ 
		        for (int y = 0; y < 3; y++) { 
		            int value = tiles[x][y]; 
		            if (value != 0) { 
		                int targetX = (value - 1) / 3; 
		                int targetY = (value - 1) % 3; 
		                int dx = x - targetX; 
		                int dy = y - targetY; 
		              manhattan += Math.abs(dx) + Math.abs(dy); 
		            } 
		        }
		}
		return manhattan;
	}	
	int [][] stringToDoubleArray (String newBoardUp) {
		int[][] convertTotiles = new int [3][3];
		int l=0;
		int m=1;
		for(int i=0; i<3; i++) {
		    for(int j=0; j<3; j++) {
		    	convertTotiles[i][j] = Integer.parseInt(newBoardUp.substring(l,m));
		    	l++;
		    	m++;
		    }
		}
		return convertTotiles;
	}
	void check(String newStateBoard){
		if (newStateBoard.equals(GoalStateBoard)) {
			System.out.println("Solution Exists at branch(level cost) "+treeDepth.get(newStateBoard)+" of the tree");
			String traceStateBoard = newStateBoard;
			while (traceStateBoard != null) {
				int branchlevel = treeDepth.get(traceStateBoard);
				System.out.println(traceStateBoard.substring(0,1)+ " " +traceStateBoard.substring(1,2)+ 
         			   " "+traceStateBoard.substring(2,3));
         	    System.out.println(traceStateBoard.substring(3,4)+ " " +traceStateBoard.substring(4,5)+ 
         			   " "+traceStateBoard.substring(5,6)+"     at cost: "+branchlevel);
         	    System.out.println(traceStateBoard.substring(6,7)+ " " +traceStateBoard.substring(7,8)+ " "
         			   +traceStateBoard.substring(8,9));
         	    System.out.println("");
                traceStateBoard = stateLog.get(traceStateBoard);
			}
			System.exit(0);
		}
	}
	String  getleastCostSucessor() {
		String leastCostSucessor =" ";
		int size = heuristic.size();
		if (size > 0) {
			Entry<String, Integer> min = Collections.min(heuristic.entrySet(), new Comparator<Entry<String, Integer>>()
					{
				       public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
				    	   return entry1.getValue().compareTo(entry2.getValue());
				       }
				
					});
			leastCostSucessor = min.getKey();
			
		}else{
			System.out.println("Solution doesn't exist ...");
			System.exit(0);
		}
		System.out.println("");
		System.out.println("heuristic: " + Arrays.asList(heuristic));
		System.out.println("leastCostSucessor: "+ leastCostSucessor);
		System.out.println("");
		heuristic.clear();
		return leastCostSucessor;
	}
	 List<String> getMultipleleastCostSucessors() {
		
		int size = heuristic.size();
		List<String> minKeyList = new ArrayList<String>();
		if (size > 0) {
			Entry<String, Integer> minMulti = null;
			for(Entry<String, Integer> entry : heuristic.entrySet()) {
				if (minMulti == null || minMulti.getValue() >= entry.getValue()) {
					if(minMulti == null || minMulti.getValue() > entry.getValue()){
						minMulti = entry;
						minKeyList.clear();
					}
					minKeyList.add(entry.getKey());
				}
			}
			
		}else{
			System.out.println("Solution doesn't exist ..");
			System.exit(0);
		}
		heuristic.clear();
		return minKeyList;
	}
	void processSuccessors(String CurrentBoard){
		String SuccessorUp    = up    (CurrentBoard);
		heuristicCalc (SuccessorUp,CurrentBoard);
		String SuccessorDown  = down  (CurrentBoard);
		heuristicCalc (SuccessorDown,CurrentBoard);
		String SuccessorRight = right (CurrentBoard);
		heuristicCalc (SuccessorRight,CurrentBoard);
		String SuccessorLeft  = left  (CurrentBoard);
		heuristicCalc (SuccessorLeft,CurrentBoard);
	}
	
	void recursiveCalc () {
		if (SizeLCS == 1){
			LeastCostSuccessorN = LeastCostSuccessorsN.get(0);
            return;
        }else {
             for (int i=0; i < LeastCostSuccessorsN.size(); i++ ){
                 String CurrentBoardProcess = LeastCostSuccessorsN.get(i);
                 processSuccessors( CurrentBoardProcess);
             }
          LeastCostSuccessorsN = getMultipleleastCostSucessors();
          SizeLCS = LeastCostSuccessorsN.size();
          recursiveCalc ();
        }
	}
}
