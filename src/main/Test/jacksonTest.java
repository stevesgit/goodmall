import org.codehaus.jackson.map.ObjectMapper;

public class jacksonTest {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Person person = new Person();
        person.setName("Tom");
        person.setAge(40);
        String jsonString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(person);
        System.out.println(jsonString);
        Person deserializedPerson = mapper.readValue(jsonString, Person.class);
        System.out.println(deserializedPerson);
    }

    public static class Person {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
