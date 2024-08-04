import java.util.*;

public class CourseScheduler {
    
    public static List<Integer> findOrder(int numCourses, int[][] prerequisites) {
        // Create an adjacency list and an in-degree array
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        int[] inDegree = new int[numCourses];
        
        // Initialize the adjacency list
        for (int i = 0; i < numCourses; i++) {
            adjList.put(i, new ArrayList<>());
        }
        
        // Build the graph
        for (int[] prereq : prerequisites) {
            int course = prereq[0];
            int prerequisite = prereq[1];
            adjList.get(prerequisite).add(course);
            inDegree[course]++;
        }
        
        // Initialize the queue with nodes having zero in-degree
        Queue<Integer> zeroInDegreeQueue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                zeroInDegreeQueue.offer(i);
            }
        }
        
        List<Integer> topOrder = new ArrayList<>();
        
        while (!zeroInDegreeQueue.isEmpty()) {
            int course = zeroInDegreeQueue.poll();
            topOrder.add(course);
            
            // Decrease in-degree of all its dependent courses
            for (int neighbor : adjList.get(course)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    zeroInDegreeQueue.offer(neighbor);
                }
            }
        }
        
        // Check if the topological sort is possible
        if (topOrder.size() == numCourses) {
            return topOrder;
        } else {
            return new ArrayList<>();  // Return empty list if there's a cycle
        }
    }

    public static void main(String[] args) {
        int numCourses = 6;
        int[][] prerequisites = {
            {1, 0},
            {2, 0},
            {3, 1},
            {3, 2},
            {4, 2},
            {5, 4}
        };

        List<Integer> order = findOrder(numCourses, prerequisites);
        
        if (order.isEmpty()) {
            System.out.println("It's not possible to complete all courses due to cyclic dependencies.");
        } else {
            System.out.println("Order of courses: " + order);
        }
    }
}