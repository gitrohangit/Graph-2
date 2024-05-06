// Time Complexity : O(V+E) single DFS traversal
// Space Complexity : O(V+E) , adjajency list

// Did this code successfully run on Leetcode : yes

// Approach: Tarjan's algorithm -  Do a single traversal forward going dfs.  A connection part of a cycle can not be critical. In order to find which nodes are part of cycle - 
//If at any point, we come across a neighbor that has a rank lower than the current node's rank, we know that the neighbor must have already been visited. 

class Solution {
    List<List<Integer>> result;
    HashMap<Integer, List<Integer>> adj;
    int[] discovery; // natural order of nodes 
    int[] lowest; // order of finding already discoverable nodes
    int time; // rank
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        discovery = new int[n];
        lowest = new int[n];
        time=0;
        adj = new HashMap<>();

        Arrays.fill(discovery,-1);
        buildGraph(connections);

        result = new ArrayList<>();
        dfs(0,0);

        return result;
    }

    private void dfs(int curr, int parent){
        //base
        if(discovery[curr] != -1) return;

        //logic
        discovery[curr] = time;
        lowest[curr] = time;
        time++;
        for(int n : adj.get(curr)){
            if(n == parent) continue; //forward going DFS
            dfs(n, curr);

            if(lowest[n] > discovery[curr]){ // critical Connection - only way to reach the node
                result.add(Arrays.asList(n, curr));
            }

            lowest[curr] = Math.min(lowest[curr],lowest[n]);// in case of cycle we will be reaching the earliest discover node first.
        }

    }

    //build adjajency map for undirected graph
    private void buildGraph(List<List<Integer>> connections){

        for(List<Integer> edge : connections){
            int v1  = edge.get(0);
            int v2  = edge.get(1);

            if(!adj.containsKey(v1)){
                adj.put(v1, new ArrayList<Integer>());
            }
            adj.get(v1).add(v2);

            if(!adj.containsKey(v2)){
                adj.put(v2, new ArrayList<Integer>());
            }
            adj.get(v2).add(v1);
        }
    }
}