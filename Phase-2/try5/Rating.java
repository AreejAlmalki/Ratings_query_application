public class Rating {
	private int userId;
	private int itemId;
	private int value; // The value of the rating
	
	// Constructor
	public Rating(int userId, int itemId, int value){
          this.itemId=itemId;
          this.userId=userId;
          this.value=value;
        }
	public int getUserId(){
            return userId;
        }
        public int getItemId() {
            return itemId; 
        }
            public int getValue(){
             return value;      
            }
            public String toString(){
                return userId+"  "+itemId+"  "+value;
            }
            
}
