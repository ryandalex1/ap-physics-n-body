public class Planet{

  public double xxPos;
  public double yyPos;
  public double xxVel;
  public double yyVel;
  public double mass;
  public String imgFileName;

  public static final double G = 6.67e-11;

  public Planet(double xP, double yP, double xV, double yV, double m, String img){
    xxPos = xP;
    yyPos = yP;
    xxVel = xV;
    yyVel = yV;
    mass = m;
    imgFileName = img;
  }

  public Planet(Planet p){
    xxPos = p.xxPos;
    yyPos = p.yyPos;
    xxVel = p.xxVel;
    yyVel = p.yyVel;
    mass = p.mass;
    imgFileName = p.imgFileName;
  }

  // Calculates the distance between this planet and the planet p
  public double calcDistance(Planet p){
    double dx = p.xxPos - this.xxPos;
    double dy = p.yyPos - this.yyPos;
    return Math.sqrt((dx * dx) + (dy * dy));
  }

  // Calculates the force exerted on this planet by the planet p
  public double calcForceExertedBy(Planet p){
    return (G * this.mass * p.mass) / (calcDistance(p) * calcDistance(p));
  }

  // Calculates the force in the X direction exerted on this planet by the planet p
  public double calcXForceExertedBy(Planet p){
    double dx = p.xxPos - this.xxPos;
    double force = this.calcForceExertedBy(p);
    double dist = this.calcDistance(p);

    return (force * dx)/dist;
  }

  // Calculates the force in the Y direction exerted on this planet by the planet p
  public double calcYForceExertedBy(Planet p){
    double dy = p.yyPos - this.yyPos;
    double force = this.calcForceExertedBy(p);
    double dist = this.calcDistance(p);

    return (force * dy)/dist;
  }

  // Calculates the net force in the X direction exerted on this planet by all planets present
  public double calcNetXForceExertedBy(Planet[] planets){
    double netForce = 0;
    for (Planet planet : planets) {
      if (!planet.equals(this)) {
          netForce += this.calcXForceExertedBy(planet);
        }
      }
    return netForce;
  }

  // Calculates the net force in the Y direction exerted on this planet by all planets present
  public double calcNetYForceExertedBy(Planet[] planets){
    double netForce = 0;
    for (Planet planet : planets) {
      if (!planet.equals(this)) {
        netForce += this.calcYForceExertedBy(planet);
      }
    }
    return netForce;
  }

  // Updates the planets position in a given period of time dt
  // with forces fX and fY applied in the X and Y direction respectively
  public void update(double dt, double fX, double fY){
    double aX = fX/this.mass;
    double aY = fY/this.mass;

    this.xxVel = this.xxVel + (dt * aX);
    this.yyVel = this.yyVel + (dt * aY);

    this.xxPos = this.xxPos + (dt * this.xxVel);
    this.yyPos = this.yyPos + (dt * this.yyVel);

  }

  // Draws the planet at its position
  public void draw(){
    StdDraw.picture(this.xxPos,this.yyPos, "images/" + this.imgFileName);
  }
}
