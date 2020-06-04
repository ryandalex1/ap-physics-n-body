public class NBody{

  public static Double readRadius(String dataFile){
    In data = new In(dataFile);
    data.readInt();
    return data.readDouble();
  }

  public static Planet[] readPlanets(String dataFile){

    In data = new In(dataFile);
    int numOfPlanets = data.readInt();
    data.readDouble();
    Planet[] planets = new Planet[numOfPlanets];

    for (int i = 0; i < numOfPlanets; i += 1) {
      double xxPos = data.readDouble();
      double yyPos = data.readDouble();
      double xxVel = data.readDouble();
      double yyVel = data.readDouble();
      double mass = data.readDouble();
      String imgFile = data.readString();
      Planet x = new Planet(xxPos,yyPos,xxVel,yyVel,mass,imgFile);
      planets[i] = x;
      }
    return planets;
  }

  public static void main (String[] args){
    double T = Double.parseDouble(args[0]);
    double dT = Double.parseDouble(args[1]);
    String filename = args[2];

    Planet[] planets = readPlanets(filename);
    double universeRadius = readRadius(filename);

    double time = 0;

    StdDraw.enableDoubleBuffering();
    StdDraw.setScale(-universeRadius, universeRadius);

    while(time<T) {
      StdDraw.clear();
      StdDraw.picture(0,0,"./images/starfield.jpg");

      double[] xForces = new double[planets.length];
      double[] yForces = new double[planets.length];

      // Calculates the net forces on each planet and stores them in arrays
      for(int i = planets.length-1; i > -1; i -= 1) {
        xForces[i] = planets[i].calcNetXForceExertedBy(planets);
        yForces[i] = planets[i].calcNetYForceExertedBy(planets);
      }

      // Calculates the new position of each planet given the new forces acting on it
      for(int i = planets.length-1; i > -1; i -= 1) {
        planets[i].update(dT,xForces[i],yForces[i]);
      }

      // Draws each planet at its new position
      for(int i = planets.length-1; i > -1; i -= 1) {
        planets[i].draw();
      }

      StdDraw.show();
      StdDraw.pause(10);
      time += dT;
    }

    StdOut.printf("%d\n", planets.length);
    StdOut.printf("%.2e\n", universeRadius);

    for (Planet planet : planets) {
      StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
              planet.xxPos, planet.yyPos, planet.xxVel,
              planet.yyVel, planet.mass, planet.imgFileName);
    }
  }
}
