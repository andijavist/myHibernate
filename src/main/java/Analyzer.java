public class Analyzer {
    public void analyzer(Class<?> clazz) {
        if(clazz.isAnnotationPresent(NameTable.class))
            System.out.println("приветы");
    }
}
