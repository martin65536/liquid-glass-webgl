package defpackage;

import android.content.ContentProviderClient;
import android.content.res.TypedArray;
import android.drm.DrmManagerClient;
import android.graphics.Path;
import android.graphics.RectF;
import android.media.MediaDrm;
import android.media.MediaMetadataRetriever;
import java.util.concurrent.ExecutorService;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract /* synthetic */ class d3 {
    public static /* synthetic */ boolean A(Object obj) {
        if (obj != null) {
            return true;
        }
        return false;
    }

    public static p60 B(j2 j2Var, int i) {
        gv gvVar;
        m70 m70Var = (m70) j2Var.f;
        ww0 t = t20.t();
        if (t != null) {
            gvVar = t.e();
        } else {
            gvVar = null;
        }
        ww0 C = t20.C(t);
        try {
            h70 h70Var = (h70) m70Var.f.getValue();
            t20.K(t, C, gvVar);
            q60 q60Var = m70Var.p;
            long j = h70Var.j;
            boolean z = m70Var.d;
            pb pbVar = new pb(i, h70Var);
            c9 c9Var = q60Var.c;
            if (c9Var != null) {
                r7 r7Var = q60Var.b;
                in0 in0Var = (in0) c9Var.d;
                boolean z2 = in0Var instanceof d6;
                hn0 hn0Var = new hn0(c9Var, i, r7Var, pbVar);
                hn0Var.h = new si(j);
                if (z2) {
                    if (z) {
                        d6 d6Var = (d6) in0Var;
                        d6Var.f.add(new rn0(1, hn0Var));
                        if (!d6Var.g) {
                            d6Var.g = true;
                            d6Var.e.post(d6Var);
                        }
                    } else {
                        d6 d6Var2 = (d6) in0Var;
                        d6Var2.f.add(new rn0(0, hn0Var));
                        if (!d6Var2.g) {
                            d6Var2.g = true;
                            d6Var2.e.post(d6Var2);
                        }
                    }
                } else {
                    in0Var.a(hn0Var);
                }
                f31.V("compose:lazy:schedule_prefetch:index", i);
                return hn0Var;
            }
            return x1.E;
        } catch (Throwable th) {
            t20.K(t, C, gvVar);
            throw th;
        }
    }

    public static cd0 C() {
        return new o50(1.0f);
    }

    public static boolean a(z6 z6Var, long j) {
        if (j >= z6Var.c()) {
            return true;
        }
        return false;
    }

    public static a11 b(a11 a11Var, a11 a11Var2) {
        boolean z = a11Var2 instanceof vb;
        if (z && (a11Var instanceof vb)) {
            vb vbVar = (vb) a11Var2;
            qv0 qv0Var = vbVar.a;
            float f = vbVar.b;
            if (Float.isNaN(f)) {
                f = ((vb) a11Var).b;
            }
            return new vb(qv0Var, f);
        }
        if (z && !(a11Var instanceof vb)) {
            return a11Var2;
        }
        if (!z && (a11Var instanceof vb)) {
            return a11Var;
        }
        return a11Var2.c(new f6(11, a11Var));
    }

    public static int c(float f, mm mmVar) {
        float G = mmVar.G(f);
        if (Float.isInfinite(G)) {
            return Integer.MAX_VALUE;
        }
        return Math.round(G);
    }

    public static cd0 d(cd0 cd0Var, cd0 cd0Var2) {
        if (cd0Var2 == zc0.a) {
            return cd0Var;
        }
        return new lf(cd0Var, cd0Var2);
    }

    public static float e(mm mmVar, long j) {
        if (!u11.a(t11.b(j), 4294967296L)) {
            s00.b("Only Sp can convert to Px");
        }
        float[] fArr = ju.a;
        if (mmVar.y() >= 1.03f) {
            iu a = ju.a(mmVar.y());
            if (a == null) {
                return mmVar.y() * t11.c(j);
            }
            return a.b(t11.c(j));
        }
        return mmVar.y() * t11.c(j);
    }

    public static long f(mm mmVar, long j) {
        if (j == 9205357640488583168L) {
            return 9205357640488583168L;
        }
        return k81.c(mmVar.o0(Float.intBitsToFloat((int) (j >> 32))), mmVar.o0(Float.intBitsToFloat((int) (j & 4294967295L))));
    }

    public static float g(mm mmVar, long j) {
        if (!u11.a(t11.b(j), 4294967296L)) {
            s00.b("Only Sp can convert to Px");
        }
        return mmVar.G(mmVar.O(j));
    }

    public static long h(mm mmVar, long j) {
        if (j == 9205357640488583168L) {
            return 9205357640488583168L;
        }
        float G = mmVar.G(Float.intBitsToFloat((int) (j >> 32)));
        float G2 = mmVar.G(Float.intBitsToFloat((int) (j & 4294967295L)));
        return (Float.floatToRawIntBits(G) << 32) | (Float.floatToRawIntBits(G2) & 4294967295L);
    }

    public static long i(float f, mm mmVar) {
        float y;
        float[] fArr = ju.a;
        if (mmVar.y() >= 1.03f) {
            iu a = ju.a(mmVar.y());
            if (a != null) {
                y = a.a(f);
            } else {
                y = f / mmVar.y();
            }
            return d20.A(4294967296L, y);
        }
        return d20.A(4294967296L, f / mmVar.y());
    }

    public static long j(long j) {
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32)) - Float.intBitsToFloat(0);
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L)) - Float.intBitsToFloat(0);
        return (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32);
    }

    public static void k(y5 y5Var, y5 y5Var2) {
        y5Var.a.addPath(y5Var2.a, Float.intBitsToFloat(0), Float.intBitsToFloat(0));
    }

    public static void l(y5 y5Var, gr0 gr0Var) {
        Path.Direction direction;
        if (y5Var.b == null) {
            y5Var.b = new RectF();
        }
        RectF rectF = y5Var.b;
        rectF.getClass();
        float f = gr0Var.a;
        long j = gr0Var.h;
        long j2 = gr0Var.g;
        long j3 = gr0Var.f;
        long j4 = gr0Var.e;
        rectF.set(f, gr0Var.b, gr0Var.c, gr0Var.d);
        if (y5Var.c == null) {
            y5Var.c = new float[8];
        }
        float[] fArr = y5Var.c;
        fArr.getClass();
        fArr[0] = Float.intBitsToFloat((int) (j4 >> 32));
        fArr[1] = Float.intBitsToFloat((int) (j4 & 4294967295L));
        fArr[2] = Float.intBitsToFloat((int) (j3 >> 32));
        fArr[3] = Float.intBitsToFloat((int) (j3 & 4294967295L));
        fArr[4] = Float.intBitsToFloat((int) (j2 >> 32));
        fArr[5] = Float.intBitsToFloat((int) (j2 & 4294967295L));
        fArr[6] = Float.intBitsToFloat((int) (j >> 32));
        fArr[7] = Float.intBitsToFloat((int) (j & 4294967295L));
        Path path = y5Var.a;
        RectF rectF2 = y5Var.b;
        rectF2.getClass();
        float[] fArr2 = y5Var.c;
        fArr2.getClass();
        int ordinal = kk0.e.ordinal();
        if (ordinal != 0) {
            if (ordinal == 1) {
                direction = Path.Direction.CW;
            } else {
                v7.k();
                return;
            }
        } else {
            direction = Path.Direction.CCW;
        }
        path.addRoundRect(rectF2, fArr2, direction);
    }

    public static void n(b50 b50Var, long j, float f) {
        long W = b50Var.e.W();
        yc ycVar = b50Var.e;
        ycVar.getClass();
        ycVar.e.c.b(f, W, yc.r(ycVar, j, yr.s, 1.0f, 3));
    }

    public static void o(up upVar, o5 o5Var, long j, long j2, float f, te teVar, int i, int i2) {
        long j3;
        int i3;
        if ((i2 & 16) != 0) {
            j3 = j;
        } else {
            j3 = j2;
        }
        if ((i2 & 512) != 0) {
            i3 = 1;
        } else {
            i3 = i;
        }
        upVar.V(o5Var, 0L, j, j3, f, teVar, i3);
    }

    public static /* synthetic */ void p(up upVar, y5 y5Var, jc0 jc0Var, float f, cz0 cz0Var, int i) {
        jc0 jc0Var2 = cz0Var;
        if ((i & 8) != 0) {
            jc0Var2 = yr.s;
        }
        upVar.w(y5Var, jc0Var, f, jc0Var2);
    }

    public static /* synthetic */ void q(up upVar, long j, long j2, float f, int i, int i2) {
        long j3;
        float f2;
        int i3;
        if ((i2 & 4) != 0) {
            j3 = j(upVar.j());
        } else {
            j3 = j2;
        }
        if ((i2 & 8) != 0) {
            f2 = 1.0f;
        } else {
            f2 = f;
        }
        yr yrVar = yr.s;
        if ((i2 & 64) != 0) {
            i3 = 3;
        } else {
            i3 = i;
        }
        upVar.a0(j, 0L, j3, f2, yrVar, i3);
    }

    public static void r(a70 a70Var, gg ggVar) {
        a70Var.a.a(1, new r7((Object) null, new pb(9), new gg(-857469575, true, new w5(1, ggVar)), 5));
    }

    public static int s(float f, int i, int i2) {
        return (Float.floatToIntBits(f) + i) * i2;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [rf, java.lang.RuntimeException] */
    public static rf t(String str) {
        q00.c(str);
        return new RuntimeException();
    }

    public static String u(String str, int i, String str2, int i2) {
        return str + i + str2 + i2;
    }

    public static String v(StringBuilder sb, float f, char c) {
        sb.append(f);
        sb.append(c);
        return sb.toString();
    }

    public static void w(int i, int i2, int i3, int i4, int i5) {
        y20.a(i);
        y20.a(i2);
        y20.a(i3);
        y20.a(i4);
        y20.a(i5);
    }

    public static void x(int i, bw bwVar, kf kfVar, bw bwVar2, w3 w3Var) {
        m20.F(kfVar, bwVar, Integer.valueOf(i));
        m20.C(w3Var, bwVar2);
    }

    public static /* synthetic */ void y(AutoCloseable autoCloseable) {
        if (autoCloseable instanceof AutoCloseable) {
            autoCloseable.close();
            return;
        }
        if (autoCloseable instanceof ExecutorService) {
            x0.n((ExecutorService) autoCloseable);
            return;
        }
        if (autoCloseable instanceof TypedArray) {
            ((TypedArray) autoCloseable).recycle();
            return;
        }
        if (autoCloseable instanceof MediaMetadataRetriever) {
            ((MediaMetadataRetriever) autoCloseable).release();
            return;
        }
        if (autoCloseable instanceof MediaDrm) {
            ((MediaDrm) autoCloseable).release();
        } else if (autoCloseable instanceof DrmManagerClient) {
            ((DrmManagerClient) autoCloseable).release();
        } else {
            if (autoCloseable instanceof ContentProviderClient) {
                ((ContentProviderClient) autoCloseable).release();
                return;
            }
            throw new IllegalArgumentException();
        }
    }

    public static /* synthetic */ void z(Object obj) {
        if (obj == null) {
            return;
        }
        v7.d();
    }
}
