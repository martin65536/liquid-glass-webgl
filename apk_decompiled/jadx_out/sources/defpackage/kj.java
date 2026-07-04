package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class kj {
    public static final kj l = new kj();
    public final double a;
    public final double b;
    public final double c;
    public final double d;
    public final double e;
    public final double f;
    public final double g;
    public final double h;
    public final double i;
    public final double j;
    public final double[][][] k;

    public kj() {
        double cos = Math.cos(0.39269908169872414d);
        this.a = cos;
        double sin = Math.sin(0.39269908169872414d);
        this.b = sin;
        double tan = 1.0d / Math.tan(0.39269908169872414d);
        this.c = tan;
        double d = cos * cos;
        this.d = d;
        double d2 = sin * sin;
        this.e = d2;
        double d3 = d2 * sin;
        this.f = d3;
        double d4 = d2 + 9.0d;
        this.g = (((((2.8284271247461903d * cos) * d4) + ((((1.4142135623730951d - r21) * 2.0d) * d3) - 9.0d)) - (((d2 * 2.0d) + 9.0d) * (d * 2.0d))) * sin * 2.0d) + ((((8.485281374238571d * d) + (1.4142135623730951d - (6.0d * cos))) - ((d * cos) * 4.0d)) * 27.0d * tan);
        this.h = ((((d * (-1.1715728752538097d)) + ((1.6568542494923806d * cos) - 0.5857864376269049d)) * (-81.0d)) * tan) - (((((-0.5857864376269049d) * cos) * d4) + ((d3 * 1.4142135623730951d) + 3.727922061357857d)) * (4.0d * sin));
        this.i = ((sin * (-0.3431457505076194d)) + (((cos * (-0.3431457505076194d)) + 0.24264068711928566d) * 9.0d * tan)) * 9.0d;
        this.j = tan * 2.7136367114850355d;
        this.k = new double[][][]{new double[][]{a(0.0d), b(0.0d, 1.0d)}, new double[][]{b(1.0d, 0.0d), a(1.0d)}};
    }

    public final double[] a(double d) {
        double d2 = -(0.6666666666666666d * d);
        double d3 = this.f;
        double d4 = this.b;
        double l2 = dl.l(this.j, this.i, (8.0d * d2 * d3 * d4) + this.h, this.g);
        double d5 = (((-0.7071067811865476d) + d4) / l2) + 0.7071067811865476d;
        double d6 = this.a;
        double d7 = ((0.7071067811865476d - d6) / l2) + 0.2928932188134524d;
        double d8 = d5 - (this.c * d7);
        double d9 = l2 * 1.5d;
        double d10 = d8 - (((d9 * d7) * d7) / d3);
        double d11 = 1.0d - d7;
        double d12 = 1.0d - d5;
        double d13 = 1.0d - d8;
        double d14 = 1.0d - d2;
        double d15 = this.d - this.e;
        double sqrt = (Math.sqrt((d15 * d15) - ((4.0d * d9) * (-(((d12 - d7) * d6) - ((d11 - d5) * d4))))) + (-d15)) / (d9 * 2.0d);
        double d16 = d6 * sqrt;
        double d17 = sqrt * d4;
        return new double[]{d2, 0.0d, d10, 0.0d, d8, 0.0d, d5, d7, d16 + d5, d17 + d7, d11 - d17, d12 - d16, d11, d12, 1.0d, d13, 1.0d, 1.0d - d10, 1.0d, d14};
    }

    public final double[] b(double d, double d2) {
        double d3 = -(0.6666666666666666d * d);
        double d4 = this.f;
        double d5 = this.b;
        double d6 = this.h;
        double l2 = dl.l(this.j, this.i, (d3 * 8.0d * d4 * d5) + d6, this.g);
        double d7 = -(0.6666666666666666d * d2);
        double l3 = dl.l(this.j, this.i, (d7 * 8.0d * d4 * d5) + d6, this.g);
        double d8 = (-0.7071067811865476d) + d5;
        double d9 = (d8 / l2) + 0.7071067811865476d;
        double d10 = this.a;
        double d11 = 0.7071067811865476d - d10;
        double d12 = (d11 / l2) + 0.2928932188134524d;
        double d13 = this.c;
        double d14 = d9 - (d12 * d13);
        double d15 = l2 * 1.5d;
        double d16 = d14 - (((d15 * d12) * d12) / d4);
        double d17 = (d8 / l3) + 0.7071067811865476d;
        double d18 = (d11 / l3) + 0.2928932188134524d;
        double d19 = d17 - (d13 * d18);
        double d20 = l3 * 1.5d;
        double d21 = d19 - (((d20 * d18) * d18) / d4);
        double d22 = 1.0d - d18;
        double d23 = 1.0d - d17;
        double d24 = 1.0d - d19;
        double d25 = 1.0d - d7;
        double d26 = this.d - this.e;
        double d27 = d22 - d9;
        double d28 = d23 - d12;
        double d29 = -((d10 * d28) - (d5 * d27));
        double d30 = (d28 * d5) - (d27 * d10);
        double d31 = (d30 / d20) * 2.0d;
        double d32 = d15 * d20 * d20;
        double d33 = ((d26 * d26) * d26) / d32;
        double d34 = (((d29 * d26) * d26) + ((d15 * d30) * d30)) / d32;
        double d35 = (-d31) / 2.0d;
        double d36 = -d34;
        double d37 = ((d34 * d31) / 2.0d) - ((d33 * d33) / 8.0d);
        double d38 = ((d36 * 3.0d) - (d35 * d35)) / 3.0d;
        double d39 = ((d37 * 27.0d) + ((((d35 * 2.0d) * d35) * d35) - ((9.0d * d35) * d36))) / 27.0d;
        double d40 = -d38;
        double cos = (Math.cos(Math.acos((-d39) / (Math.sqrt(((d40 * d38) * d38) / 27.0d) * 2.0d)) / 3.0d) * (Math.sqrt(d40 / 3.0d) * 2.0d)) - (d35 / 3.0d);
        double sqrt = Math.sqrt((cos * 2.0d) - d31);
        double sqrt2 = (sqrt - Math.sqrt((sqrt * sqrt) - (((d33 / (sqrt * 2.0d)) + cos) * 4.0d))) / 2.0d;
        double d41 = ((-d30) - ((d20 * sqrt2) * sqrt2)) / d26;
        return new double[]{d3, 0.0d, d16, 0.0d, d14, 0.0d, d9, d12, (d41 * d10) + d9, (d41 * d5) + d12, d22 - (d5 * sqrt2), d23 - (sqrt2 * d10), d22, d23, 1.0d, d24, 1.0d, 1.0d - d21, 1.0d, d25};
    }
}
