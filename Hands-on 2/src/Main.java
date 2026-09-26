public class Main {

    // Clase que representa un patron de entrenamiento (Muestra de entrada y salida esperada)
    static class Patron {
        private final double[] x; // Entradas [x1, x2]
        private final int yEsperada; // Salida esperada (0 o 1)

        public Patron(double[] x, int yEsperada) {
            this.x = x;
            this.yEsperada = yEsperada;
        }

        public double[] getX() {
            return x;
        }

        public int getYEsperada() {
            return yEsperada;
        }
    }

    // Clase que encapsula la abstracción del perseptron Simple
    static class Perceptron {
        private double[] w;        // Pesos  (w1, w2)
        private double b;          // Bias / Sesgo
        private double alpha;      // Velocidad / Tasa de aprendizaje

        public Perceptron(int numEntradas, double alpha) {
            this.w = new double[numEntradas];
            this.alpha = alpha;

            // Iinicializacion de pesos y bias con valores aleatorios  -1.0 y 1.0
            for (int i = 0; i < numEntradas; i++) {
                this.w[i] = Math.random() * 2 - 1;
            }
            this.b = Math.random() * 2 - 1;
        }

        // Calcula la suma ponderada: net = (w1*x1 + w2*x2) + b
        public double calcularSumaPonderada(double[] x) {
            double suma = 0.0;
            for (int i = 0; i < x.length; i++) {
                suma += this.w[i] * x[i];
            }
            return suma + this.b;
        }

        // Función de activación
        public int funcionActivacion(double sumaPonderada) {
            return (sumaPonderada >= 0.0) ? 1 : 0;
        }

        // proceso de entrenamiento implementando velocidad de entrenamiento (alpha)
        public void entrenar(Patron[] conjuntoEntrenamiento, int maxEpocas) {
            boolean entrenado = false;
            int epoca = 0;

            while (!entrenado && epoca < maxEpocas) {
                int erroresTotales = 0;

                for (Patron patron : conjuntoEntrenamiento) {
                    double[] x = patron.getX();
                    int yEsperada = patron.getYEsperada();

                    double net = calcularSumaPonderada(x);
                    int yObtenida = funcionActivacion(net);

                    int error = yEsperada - yObtenida;

                    // Regla del perseptron incorporando alpha:
                    // w_i = w_i + alpha * error * x_i
                    // b = b + alpha * error
                    if (error != 0) {
                        for (int i = 0; i < w.length; i++) {
                            this.w[i] = this.w[i] + this.alpha * error * x[i];
                        }
                        this.b = this.b + this.alpha * error;
                        erroresTotales++;
                    }
                }

                // Si no ocurre error , el perceptron concluye
                if (erroresTotales == 0) {
                    entrenado = true;
                }
                epoca++;
            }
        }

        // metodo para evaluar y mostrar los calculos paso a paso
        public void probarYMostrarDetalles(Patron[] conjuntoEntrenamiento) {
            System.out.println("----------------------------------------");
            System.out.println("  valores optenidos ");
            System.out.println("=================================================");
            System.out.printf("taza de aprendizaje (alpha) : %.2f%n", alpha);
            System.out.printf("Peso w1                    : %.4f%n", w[0]);
            System.out.printf("Peso w2                    : %.4f%n", w[1]);
            System.out.printf("Bias (b)                   : %.4f%n", b);
            System.out.println("--------------------------------------------------");
            System.out.println("  calculos paso a paso");
            System.out.println("=================================================");

            for (int i = 0; i < conjuntoEntrenamiento.length; i++) {
                Patron p = conjuntoEntrenamiento[i];
                double[] x = p.getX();
                double net = calcularSumaPonderada(x);
                int yObtenida = funcionActivacion(net);

                System.out.printf("patron %d: entrada = [%.0f, %.0f] | salida Esperado = %d%n",
                        (i + 1), x[0], x[1], p.getYEsperada());
                System.out.printf("   ➜ suma ponderada (net) = (%.4f * %.0f) + (%.4f * %.0f) + (%.4f) = %.4f%n",
                        w[0], x[0], w[1], x[1], b, net);
                System.out.printf("   funcion de activacion f(%.4f) = %d%n", net, yObtenida);
                System.out.printf("   estado %s%n", (yObtenida == p.getYEsperada() ? "Correcto" : "Error"));
                System.out.println("-------------------------------------------------");
            }
        }
    }

    public static void main(String[] args) {
        // definicion de la tabla de verdad
        Patron[] datosOR = new Patron[] {
                new Patron(new double[]{0, 0}, 0),
                new Patron(new double[]{0, 1}, 1),
                new Patron(new double[]{1, 0}, 1),
                new Patron(new double[]{1, 1}, 1)
        };

        // instancia del perseptron: entradas y velocidad de aprendizaje  alpha = 0.1
        double velocidadAprendizaje = 0.1;
        Perceptron perceptron = new Perceptron(2, velocidadAprendizaje);

        // entrenamiento con un limite
        perceptron.entrenar(datosOR, 100);

        // imprime en terminal los datos optenidos
        perceptron.probarYMostrarDetalles(datosOR);
    }
}