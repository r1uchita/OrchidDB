
package orchid;


public enum data_types {
 
   VARCHAR("V", "Varchar"), NUMBER("N", "Number"), FLOAT("F", "Float"), DATE("D", "Date");
 
   private String code;
   private String type;
 
   private data_types(String code, String type) {
       this.code = code;
       this.type = type;
   }
 
   public String getCode() {
       return code;
   }
 
   public String getType() {
       return type;
   }
 
   public static data_types getByCode(String typeCode) {
       for (data_types g : data_types.values()) {
           if (g.code.equals(typeCode)) {
               return g;
           }
       }
       return null;
   }
 
   @Override
   public String toString() {
       return this.type;
   }
 
}