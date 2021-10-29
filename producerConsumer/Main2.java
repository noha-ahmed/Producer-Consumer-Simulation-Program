package producerConsumer;

public class Main2 {
    public static int inputSize=3;

    /*static void test1() throws InterruptedException {
        LineQueue q0 = new LineQueue(0);
        LineQueue q1 = new LineQueue(1);
        LineQueue q2 = new LineQueue(2);
        LineQueue q3 = new LineQueue(3);
        Machine m0 = new Machine(0);
        Machine m1 = new Machine(1);
        Machine m2 = new Machine(2);
        Machine m3 = new Machine(3);
        Machine m4 = new Machine(4);

        q0.addAttachedMachines(m0);
        q0.addAttachedMachines(m1);
        q0.addAttachedMachines(m2);
        q1.addAttachedMachines(m0);
        q1.addAttachedMachines(m1);
        q1.addAttachedMachines(m2);

        m0.addInputQueue(q0);
        m0.addInputQueue(q1);
        m1.addInputQueue(q0);
        m1.addInputQueue(q1);
        m2.addInputQueue(q0);
        m2.addInputQueue(q1);


        m0.setOutputQueue(q2);
        m1.setOutputQueue(q2);
        m2.setOutputQueue(q2);

        q2.addAttachedMachines(m3);
        q2.addAttachedMachines(m4);

        m3.addInputQueue(q2);
        m4.addInputQueue(q2);

        m3.setOutputQueue(q3);
        m4.setOutputQueue(q3);




        inputSize=5;

        q0.start();
        q1.start();
        q2.start();
        q3.start();
        m0.start();
        m1.start();
        m2.start();
        m3.start();
        m4.start();

        Product p0 = new Product();
        p0.setColor("blue");
        q0.addProduct(p0);
        Thread.sleep(1000);
        Product p1 = new Product();
        p1.setColor("red");
        q0.addProduct(p1);
        Thread.sleep(1000);
        Product p2 = new Product();
        p2.setColor("yellow");
        q0.addProduct(p2);
        Thread.sleep(1000);
        Product p3 = new Product();
        p3.setColor("brown");
        q1.addProduct(p3);
        Thread.sleep(1000);
        Product p4 = new Product();
        p4.setColor("black");
        q1.addProduct(p4);
        Thread.sleep(1000);


        while(q3.getProducts().size()!=inputSize){
            Thread.sleep(1000);
        }
    }*/

    public static void main(String args[]) throws InterruptedException {
      //  test2();
    }

   /* static void test2() throws InterruptedException {
        LineQueue q0 = new LineQueue(0);
        LineQueue q1 = new LineQueue(1);
        LineQueue q2 = new LineQueue(2);
        LineQueue q3 = new LineQueue(3);
        LineQueue q4 = new LineQueue(4);
        LineQueue q5 = new LineQueue(5);
        LineQueue q6 = new LineQueue(6);

        Machine m1 = new Machine(1);
        Machine m2 = new Machine(2);
        Machine m3 = new Machine(3);
        Machine m4 = new Machine(4);
        Machine m5 = new Machine(5);
        Machine m6 = new Machine(6);
        Machine m7 = new Machine(7);

        q0.addAttachedMachines(m1);
        q0.addAttachedMachines(m4);
        q1.addAttachedMachines(m2);
        q1.addAttachedMachines(m3);
        q3.addAttachedMachines(m5);
        q4.addAttachedMachines(m6);
        q4.addAttachedMachines(m7);
        q5.addAttachedMachines(m6);
        q5.addAttachedMachines(m7);

        m1.addInputQueue(q0);
        m1.setOutputQueue(q1);
        m2.addInputQueue(q1);
        m2.setOutputQueue(q3);
        m3.addInputQueue(q1);
        m3.setOutputQueue(q3);
        m4.addInputQueue(q0);
        m4.setOutputQueue(q4);
        m5.addInputQueue(q3);
        m5.setOutputQueue(q5);
        m6.addInputQueue(q5);
        m6.addInputQueue(q4);
        m6.setOutputQueue(q6);
        m7.addInputQueue(q5);
        m7.addInputQueue(q4);
        m7.setOutputQueue(q6);

        inputSize=5;


        q0.start();
        q1.start();
        q2.start();
        q3.start();
        q4.start();
        q5.start();
        q6.start();
        m1.start();
        m2.start();
        m3.start();
        m4.start();
        m5.start();
        m6.start();
        m7.start();

        Product p0 = new Product();
        p0.setColorName("blue");
        q0.addProduct(p0);
        Thread.sleep(1000);
        Product p1 = new Product();
        p1.setColorName("red");
        q0.addProduct(p1);
        Thread.sleep(1000);
        Product p2 = new Product();
        p2.setColorName("yellow");
        q0.addProduct(p2);
        Thread.sleep(1000);
        Product p3 = new Product();
        p3.setColorName("brown");
        q0.addProduct(p3);
        Thread.sleep(1000);
        Product p4 = new Product();
        p4.setColorName("black");
        q0.addProduct(p4);
        Thread.sleep(1000);


        while(q6.getProducts().size()!=inputSize){
            Thread.sleep(1000);
        }
    } */

}


