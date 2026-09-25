public class Main {

    // clase que representa un patron de entrenamiento (muestra la entrada y salida esperada)
 static class  patron{
     private  final double[] x; // Entradas [x1, x2]
     private final int yEsperada; // Salida esperada (0 o 1)

     public patron(double[] x, int yEsperada){
         this.x = x;
         this.yEsperada = yEsperada;
     }

     public double[] getX(){
         return x;
     }
     public int getyEsperada(){
         return yEsperada;
     }
 }

 // clase que encapsula el perceptron
    static class perceptron{
     private double [] w; // pesos (w1,w2)
     private double b; //bias

     public perceptron(int numEtradas){
         this.w = new double[numEtradas];
         //inicializacion de peso y bias
         for(int i=0; i<numEtradas;i++){
             this.w[i] =Math.random() * 2-1; // valores aleatorios entre -1 y 1
         }
         this.b = Math.random() * 2-1;
     }
     // calcula la suma ponderada : suma(W1*x1 + w2*x2) + b
     public double calcularSumaPonderada(double[] x) {
         double suma = 0.0;
         for (int i = 0; i < x.length; i++) {
             suma += this.w[i] * x[i];
         }
         return suma + this.b;
     }
     //funcion de activacion escalon
     public int funcionActivacion(double sumaPoderada){
         return (sumaPoderada>= 0.0) ? 1:0 ;
     }
     //proceso de entrenamiento del perceptron sin tasa de aprendizaje
     public void entrenar(patron[] conJuntoEntrenamiento, int maxEpocas){

         boolean entrenado = false;
         int epoca = 0;

         while (!entrenado && epoca < maxEpocas){
             int erroresTotales = 0;
             for(patron patron : conJuntoEntrenamiento){
                 double[] x = patron.getX();
                 int yEsperada = patron.getyEsperada();

                 double net = calcularSumaPonderada(x);
                 int yObtenida = funcionActivacion(net);

                 int error = yEsperada - yObtenida;
                 // si hay error,actualizamos los pesos y el bias sin usar el parametro alpha
                 if (error != 0){
                     for (int i = 0; i<w.length; i++){
                         this.w[i] = this.w[i] + error * x[i]; //  w_i = w_i + error * x_i
                     }
                     this.b = this.b + error;
                     erroresTotales++;
                 }
             }
             if (erroresTotales == 0){
                 entrenado = true;
             }
             epoca++;
         }
     }
     //metodo para evaluar y desplegar la verificacion final paso a paso

     public void probarYMostrarDetalles(patron[] conJuntoEntrenamiento){
         System.out.println("           valores optimos      ");
         System.out.println("--------------------------------");
         System.out.printf("Peso w1 : %.4f%n ", w[0]);
         System.out.printf("Peso w2 : %.4f%n ", w[1]);
         System.out.printf("Bias (b): %.4f%n ", b);
         System.out.println("--------------------------------");
         System.out.println("            Calculos            ");
         System.out.println("--------------------------------");

         for (int i = 0; i<conJuntoEntrenamiento.length; i++){
             patron p = conJuntoEntrenamiento[i];
             double[] x = p.getX();
             double net = calcularSumaPonderada(x);
             int yObtenida = funcionActivacion(net);

             System.out.printf("patron %d: entradas = [%.0f, %.0f] | salida Esperado = %d%n",
                     (i + 1), x[0], x[1], p.getyEsperada());

             System.out.printf("suma ponderada (net) = (%.4f * %.0f) + (%.4f * %.0f) + (%.4f) = %.4f%n",
                     w[0], x[0], w[1], x[1], b, net);
             System.out.printf("   funcion de activacion f(%.4f) = %d%n", net, yObtenida);
             System.out.printf("   ➜ Estado: %s%n", (yObtenida == p.getyEsperada() ? "CORRECTO" : "ERROR"));
             System.out.println("-------------------------------------------------");
         }
     }

 }


    public static void main(String[] args) {
        // Definición de la tabla de verdad de la compuerta AND
        patron[] datosAND = new patron[] {
                new patron(new double[]{0, 0}, 0),
                new patron(new double[]{0, 1}, 0),
                new patron(new double[]{1, 0}, 0),
                new patron(new double[]{1, 1}, 1)
        };

        // Crear una instancia del perceptrón con 2 entradas
        perceptron perceptron = new perceptron(2);

        // Entrenamiento con un límite de épocas
        perceptron.entrenar(datosAND, 100);

        // Mostrar resultados óptimos y verificación paso a paso
        perceptron.probarYMostrarDetalles(datosAND);
    }
}