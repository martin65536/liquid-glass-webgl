package defpackage;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Trace;
import android.util.DisplayMetrics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.locks.LockSupport;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class f31 {
    public static final c2 c;
    public static final w4 d;
    public static final r7 f;
    public static r7 g;
    public static iz n;
    public static final Object a = new Object();
    public static final c2 b = new c2(6);
    public static final b6 e = new b6(2);
    public static final StackTraceElement[] h = new StackTraceElement[0];
    public static final c4 i = new c4(21, new w4(12, 0), new pb(18));
    public static final long[] j = new long[0];
    public static final StackTraceElement[] k = new StackTraceElement[0];
    public static final v7 l = new v7(27);
    public static final v7 m = new v7(28);

    static {
        int i2 = 7;
        c = new c2(i2);
        d = new w4(i2, (byte) 0);
        Object obj = null;
        f = new r7(obj, obj, obj, 6);
    }

    public static final hm A(yj yjVar) {
        hm hmVar;
        wj j2 = yjVar.j(x1.A);
        if (j2 instanceof hm) {
            hmVar = (hm) j2;
        } else {
            hmVar = null;
        }
        if (hmVar == null) {
            return pl.a;
        }
        return hmVar;
    }

    public static final float B(long j2) {
        return Float.intBitsToFloat((int) (j2 >> 32));
    }

    public static final iz C() {
        iz izVar = n;
        if (izVar != null) {
            return izVar;
        }
        hz hzVar = new hz("Flight", 24.0f, 24.0f, 960.0f, 960.0f, false, 224);
        qx0 qx0Var = new qx0(f(4280229663L));
        int i2 = o41.a;
        fc fcVar = new fc(1);
        rk0 rk0Var = new rk0(400.0f, 552.0f);
        ArrayList arrayList = fcVar.a;
        arrayList.add(rk0Var);
        arrayList.add(new qk0(147.0f, 653.0f));
        fcVar.b(-24.0f, 10.0f, -45.5f, -4.5f);
        fcVar.c(80.0f, 608.0f);
        fcVar.d(-22.0f);
        fcVar.b(0.0f, -12.0f, 5.5f, -23.0f);
        arrayList.add(new cl0(15.5f, -18.0f));
        fcVar.a(299.0f, -209.0f);
        fcVar.d(-176.0f);
        fcVar.b(0.0f, -33.0f, 23.5f, -56.5f);
        fcVar.c(480.0f, 80.0f);
        fcVar.b(33.0f, 0.0f, 56.5f, 23.5f);
        fcVar.c(560.0f, 160.0f);
        fcVar.d(176.0f);
        fcVar.a(299.0f, 209.0f);
        fcVar.b(10.0f, 7.0f, 15.5f, 18.0f);
        arrayList.add(new cl0(5.5f, 23.0f));
        fcVar.d(22.0f);
        fcVar.b(0.0f, 26.0f, -21.5f, 40.5f);
        fcVar.c(813.0f, 653.0f);
        arrayList.add(new qk0(560.0f, 552.0f));
        fcVar.d(144.0f);
        fcVar.a(103.0f, 72.0f);
        fcVar.b(8.0f, 6.0f, 12.5f, 14.5f);
        fcVar.c(680.0f, 801.0f);
        fcVar.d(24.0f);
        fcVar.b(0.0f, 20.0f, -16.5f, 32.5f);
        fcVar.c(627.0f, 864.0f);
        fcVar.a(-147.0f, -44.0f);
        fcVar.a(-147.0f, 44.0f);
        fcVar.b(-20.0f, 6.0f, -36.5f, -6.5f);
        fcVar.c(280.0f, 825.0f);
        fcVar.d(-24.0f);
        fcVar.b(0.0f, -10.0f, 4.5f, -18.5f);
        fcVar.c(297.0f, 768.0f);
        fcVar.a(103.0f, -72.0f);
        fcVar.d(-144.0f);
        arrayList.add(nk0.c);
        if (hzVar.k) {
            q00.b("ImageVector.Builder is single use, create a new instance to create a new ImageVector");
        }
        ((gz) hzVar.i.get(r0.size() - 1)).j.add(new r41("", arrayList, 0, qx0Var, 1.0f, null, 1.0f, 0.0f, 0, 0, 4.0f, 0.0f, 1.0f, 0.0f));
        iz a2 = hzVar.a();
        n = a2;
        return a2;
    }

    public static boolean D(int i2, Object obj) {
        int i3;
        if (obj instanceof sv) {
            if (obj instanceof tv) {
                i3 = ((tv) obj).b();
            } else if (obj instanceof vu) {
                i3 = 0;
            } else if (obj instanceof gv) {
                i3 = 1;
            } else if (obj instanceof kv) {
                i3 = 2;
            } else if (obj instanceof lv) {
                i3 = 3;
            } else if (obj instanceof mv) {
                i3 = 4;
            } else if (obj instanceof nv) {
                i3 = 5;
            } else if (obj instanceof ov) {
                i3 = 6;
            } else if (obj instanceof pv) {
                i3 = 7;
            } else if (obj instanceof qv) {
                i3 = 8;
            } else if (obj instanceof rv) {
                i3 = 9;
            } else if (obj instanceof wu) {
                i3 = 10;
            } else if (obj instanceof xu) {
                i3 = 11;
            } else if (obj instanceof zu) {
                i3 = 13;
            } else if (obj instanceof av) {
                i3 = 14;
            } else if (obj instanceof bv) {
                i3 = 15;
            } else if (obj instanceof cv) {
                i3 = 16;
            } else if (obj instanceof dv) {
                i3 = 17;
            } else if (obj instanceof ev) {
                i3 = 18;
            } else if (obj instanceof fv) {
                i3 = 19;
            } else if (obj instanceof hv) {
                i3 = 20;
            } else if (obj instanceof iv) {
                i3 = 21;
            } else {
                i3 = -1;
            }
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }

    public static final boolean E(long j2) {
        if ((j2 & 2) != 0) {
            return true;
        }
        return false;
    }

    public static final boolean F(long j2) {
        if ((j2 & 1) != 0) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v4, types: [q, dy0] */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8 */
    public static dy0 G(hk hkVar, yj yjVar, kv kvVar, int i2) {
        kk kkVar;
        ?? r2;
        if ((i2 & 1) != 0) {
            yjVar = cr.e;
        }
        if ((i2 & 2) != 0) {
            kkVar = kk.e;
        } else {
            kkVar = kk.h;
        }
        yj I = I(hkVar, yjVar);
        if (kkVar == kk.f) {
            r2 = new q70(I, kvVar);
        } else {
            r2 = new q(I, true);
        }
        r2.o0(kkVar, r2, kvVar);
        return r2;
    }

    public static final cd0 H(cd0 cd0Var, c40 c40Var) {
        cd0Var.getClass();
        c40Var.getClass();
        return cd0Var.b(new d40(c40Var));
    }

    public static final yj I(hk hkVar, yj yjVar) {
        yj z = z(hkVar.g(), yjVar, true);
        bm bmVar = mn.a;
        if (z != bmVar && z.j(x1.A) == null) {
            return z.i(bmVar);
        }
        return z;
    }

    public static final long J(long j2, long j3) {
        float intBitsToFloat = Float.intBitsToFloat((int) (j2 >> 32)) + ((int) (j3 >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j2 & 4294967295L)) + ((int) (j3 & 4294967295L));
        return (Float.floatToRawIntBits(intBitsToFloat) << 32) | (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L);
    }

    public static final void K(uw0 uw0Var, int i2, Object obj) {
        int h2 = uw0Var.h(i2);
        Object[] objArr = uw0Var.c;
        Object obj2 = objArr[h2];
        objArr[h2] = ph.a;
        if (obj == obj2) {
            return;
        }
        rh.a("Slot table is out of sync (expected " + obj + ", got " + obj2 + ')');
    }

    public static final long L(long j2) {
        return (Math.round(Float.intBitsToFloat((int) (j2 & 4294967295L))) & 4294967295L) | (Math.round(Float.intBitsToFloat((int) (j2 >> 32))) << 32);
    }

    public static final Object M(yj yjVar, kv kvVar) {
        nr nrVar;
        yj z;
        long j2;
        qf qfVar;
        Thread currentThread = Thread.currentThread();
        xj xjVar = x1.A;
        ak akVar = (ak) yjVar.j(xjVar);
        cr crVar = cr.e;
        if (akVar == null) {
            nrVar = w11.a();
            z = z(crVar, yjVar.i(nrVar), true);
            bm bmVar = mn.a;
            if (z != bmVar && z.j(xjVar) == null) {
                z = z.i(bmVar);
            }
        } else {
            nrVar = (nr) w11.a.get();
            z = z(crVar, yjVar, true);
            bm bmVar2 = mn.a;
            if (z != bmVar2 && z.j(xjVar) == null) {
                z = z.i(bmVar2);
            }
        }
        ga gaVar = new ga(z, currentThread, nrVar);
        gaVar.o0(kk.e, gaVar, kvVar);
        nr nrVar2 = gaVar.k;
        if (nrVar2 != null) {
            int i2 = nr.j;
            nrVar2.u(false);
        }
        while (!Thread.interrupted()) {
            try {
                if (nrVar2 != null) {
                    j2 = nrVar2.v();
                } else {
                    j2 = Long.MAX_VALUE;
                }
                if (gaVar.Q() instanceof sz) {
                    LockSupport.parkNanos(gaVar, j2);
                } else {
                    if (nrVar2 != null) {
                        int i3 = nr.j;
                        nrVar2.q(false);
                    }
                    Object K = o20.K(gaVar.Q());
                    if (K instanceof qf) {
                        qfVar = (qf) K;
                    } else {
                        qfVar = null;
                    }
                    if (qfVar == null) {
                        return K;
                    }
                    throw qfVar.a;
                }
            } catch (Throwable th) {
                if (nrVar2 != null) {
                    int i4 = nr.j;
                    nrVar2.q(false);
                }
                throw th;
            }
        }
        InterruptedException interruptedException = new InterruptedException();
        gaVar.D(interruptedException);
        throw interruptedException;
    }

    public static void N(Object obj, String str) {
        String name;
        if (obj == null) {
            name = "null";
        } else {
            name = obj.getClass().getName();
        }
        ClassCastException classCastException = new ClassCastException(name + " cannot be cast to " + str);
        o20.A(classCastException, f31.class.getName());
        throw classCastException;
    }

    public static final BlendMode O(int i2) {
        BlendMode blendMode;
        BlendMode blendMode2;
        BlendMode blendMode3;
        BlendMode blendMode4;
        BlendMode blendMode5;
        BlendMode blendMode6;
        BlendMode blendMode7;
        BlendMode blendMode8;
        BlendMode blendMode9;
        BlendMode blendMode10;
        BlendMode blendMode11;
        BlendMode blendMode12;
        BlendMode blendMode13;
        BlendMode blendMode14;
        BlendMode blendMode15;
        BlendMode blendMode16;
        BlendMode blendMode17;
        BlendMode blendMode18;
        BlendMode blendMode19;
        BlendMode blendMode20;
        BlendMode blendMode21;
        BlendMode blendMode22;
        BlendMode blendMode23;
        BlendMode blendMode24;
        BlendMode blendMode25;
        BlendMode blendMode26;
        BlendMode blendMode27;
        BlendMode blendMode28;
        BlendMode blendMode29;
        BlendMode blendMode30;
        if (i2 == 0) {
            blendMode30 = BlendMode.CLEAR;
            return blendMode30;
        }
        if (i2 == 1) {
            blendMode29 = BlendMode.SRC;
            return blendMode29;
        }
        if (i2 == 2) {
            blendMode28 = BlendMode.DST;
            return blendMode28;
        }
        if (i2 == 3) {
            blendMode27 = BlendMode.SRC_OVER;
            return blendMode27;
        }
        if (i2 == 4) {
            blendMode26 = BlendMode.DST_OVER;
            return blendMode26;
        }
        if (i2 == 5) {
            blendMode25 = BlendMode.SRC_IN;
            return blendMode25;
        }
        if (i2 == 6) {
            blendMode24 = BlendMode.DST_IN;
            return blendMode24;
        }
        if (i2 == 7) {
            blendMode23 = BlendMode.SRC_OUT;
            return blendMode23;
        }
        if (i2 == 8) {
            blendMode22 = BlendMode.DST_OUT;
            return blendMode22;
        }
        if (i2 == 9) {
            blendMode21 = BlendMode.SRC_ATOP;
            return blendMode21;
        }
        if (i2 == 10) {
            blendMode20 = BlendMode.DST_ATOP;
            return blendMode20;
        }
        if (i2 == 11) {
            blendMode19 = BlendMode.XOR;
            return blendMode19;
        }
        if (i2 == 12) {
            blendMode18 = BlendMode.PLUS;
            return blendMode18;
        }
        if (i2 == 13) {
            blendMode17 = BlendMode.MODULATE;
            return blendMode17;
        }
        if (i2 == 14) {
            blendMode16 = BlendMode.SCREEN;
            return blendMode16;
        }
        if (i2 == 15) {
            blendMode15 = BlendMode.OVERLAY;
            return blendMode15;
        }
        if (i2 == 16) {
            blendMode14 = BlendMode.DARKEN;
            return blendMode14;
        }
        if (i2 == 17) {
            blendMode13 = BlendMode.LIGHTEN;
            return blendMode13;
        }
        if (i2 == 18) {
            blendMode12 = BlendMode.COLOR_DODGE;
            return blendMode12;
        }
        if (i2 == 19) {
            blendMode11 = BlendMode.COLOR_BURN;
            return blendMode11;
        }
        if (i2 == 20) {
            blendMode10 = BlendMode.HARD_LIGHT;
            return blendMode10;
        }
        if (i2 == 21) {
            blendMode9 = BlendMode.SOFT_LIGHT;
            return blendMode9;
        }
        if (i2 == 22) {
            blendMode8 = BlendMode.DIFFERENCE;
            return blendMode8;
        }
        if (i2 == 23) {
            blendMode7 = BlendMode.EXCLUSION;
            return blendMode7;
        }
        if (i2 == 24) {
            blendMode6 = BlendMode.MULTIPLY;
            return blendMode6;
        }
        if (i2 == 25) {
            blendMode5 = BlendMode.HUE;
            return blendMode5;
        }
        if (i2 == 26) {
            blendMode4 = BlendMode.SATURATION;
            return blendMode4;
        }
        if (i2 == 27) {
            blendMode3 = BlendMode.COLOR;
            return blendMode3;
        }
        if (i2 == 28) {
            blendMode2 = BlendMode.LUMINOSITY;
            return blendMode2;
        }
        blendMode = BlendMode.SRC_OVER;
        return blendMode;
    }

    public static final int P(long j2) {
        float[] fArr = af.a;
        return (int) (se.a(j2, af.e) >>> 32);
    }

    public static final Bitmap.Config Q(int i2) {
        Bitmap.Config config;
        Bitmap.Config config2;
        if (i2 == 0) {
            return Bitmap.Config.ARGB_8888;
        }
        if (i2 == 1) {
            return Bitmap.Config.ALPHA_8;
        }
        if (i2 == 2) {
            return Bitmap.Config.RGB_565;
        }
        int i3 = Build.VERSION.SDK_INT;
        if (i3 >= 26 && i2 == 3) {
            config2 = Bitmap.Config.RGBA_F16;
            return config2;
        }
        if (i3 >= 26 && i2 == 4) {
            config = Bitmap.Config.HARDWARE;
            return config;
        }
        return Bitmap.Config.ARGB_8888;
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0085, code lost:
    
        if (r8 > 4611686018427387903L) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x009f, code lost:
    
        if (r8 > 4611686018427387903L) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final long R(long r8, defpackage.cq r10) {
        /*
            java.util.concurrent.TimeUnit r0 = r10.e
            r1 = 4611686018426999999(0x3ffffffffffa14bf, double:1.9999999999138678)
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.NANOSECONDS
            long r1 = r0.convert(r1, r3)
            long r4 = -r1
            int r4 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r4 > 0) goto L21
            int r1 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r1 > 0) goto L21
            long r8 = r3.convert(r8, r0)
            dt0 r10 = defpackage.aq.e
            r10 = 1
            long r8 = r8 << r10
            int r10 = defpackage.bq.a
            return r8
        L21:
            cq r1 = defpackage.cq.MILLISECONDS
            int r1 = r10.compareTo(r1)
            if (r1 < 0) goto La8
            int r0 = java.lang.Long.signum(r8)
            long r0 = (long) r0
            r2 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r4 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r4 >= 0) goto L38
            r8 = r2
        L38:
            long r8 = java.lang.Math.abs(r8)
            int r2 = r10.ordinal()
            r3 = 2
            r4 = 0
            r6 = 1
            if (r2 == r3) goto L68
            r3 = 3
            if (r2 == r3) goto L65
            r3 = 4
            if (r2 == r3) goto L61
            r3 = 5
            if (r2 == r3) goto L5d
            r3 = 6
            if (r2 != r3) goto L57
            r2 = 86400000(0x5265c00, double:4.2687272E-316)
            goto L69
        L57:
            java.lang.String r8 = "Wrong unit for millisMultiplier: "
            defpackage.v7.e(r10, r8)
            return r4
        L5d:
            r2 = 3600000(0x36ee80, double:1.7786363E-317)
            goto L69
        L61:
            r2 = 60000(0xea60, double:2.9644E-319)
            goto L69
        L65:
            r2 = 1000(0x3e8, double:4.94E-321)
            goto L69
        L68:
            r2 = r6
        L69:
            int r10 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r10 != 0) goto L6f
        L6d:
            r8 = r4
            goto La2
        L6f:
            int r10 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            r4 = 4611686018427387903(0x3fffffffffffffff, double:1.9999999999999998)
            if (r10 != 0) goto L7f
            int r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r8 <= 0) goto L7d
            goto La1
        L7d:
            r8 = r2
            goto La2
        L7f:
            int r10 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r10 != 0) goto L88
            int r10 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r10 <= 0) goto La2
            goto La1
        L88:
            int r10 = java.lang.Long.numberOfLeadingZeros(r8)
            int r10 = 128 - r10
            int r6 = java.lang.Long.numberOfLeadingZeros(r2)
            int r10 = r10 - r6
            r6 = 63
            if (r10 >= r6) goto L99
            long r8 = r8 * r2
            goto La2
        L99:
            if (r10 <= r6) goto L9c
            goto La1
        L9c:
            long r8 = r8 * r2
            int r10 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r10 <= 0) goto La2
        La1:
            goto L6d
        La2:
            long r0 = r0 * r8
            long r8 = u(r0)
            return r8
        La8:
            java.util.concurrent.TimeUnit r10 = java.util.concurrent.TimeUnit.MILLISECONDS
            long r8 = r10.convert(r8, r0)
            long r8 = defpackage.n30.k(r8)
            long r8 = u(r8)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.f31.R(long, cq):long");
    }

    public static final PorterDuff.Mode S(int i2) {
        if (i2 == 0) {
            return PorterDuff.Mode.CLEAR;
        }
        if (i2 == 1) {
            return PorterDuff.Mode.SRC;
        }
        if (i2 == 2) {
            return PorterDuff.Mode.DST;
        }
        if (i2 == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i2 == 4) {
            return PorterDuff.Mode.DST_OVER;
        }
        if (i2 == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        if (i2 == 6) {
            return PorterDuff.Mode.DST_IN;
        }
        if (i2 == 7) {
            return PorterDuff.Mode.SRC_OUT;
        }
        if (i2 == 8) {
            return PorterDuff.Mode.DST_OUT;
        }
        if (i2 == 9) {
            return PorterDuff.Mode.SRC_ATOP;
        }
        if (i2 == 10) {
            return PorterDuff.Mode.DST_ATOP;
        }
        if (i2 == 11) {
            return PorterDuff.Mode.XOR;
        }
        if (i2 == 12) {
            return PorterDuff.Mode.ADD;
        }
        if (i2 == 14) {
            return PorterDuff.Mode.SCREEN;
        }
        if (i2 == 15) {
            return PorterDuff.Mode.OVERLAY;
        }
        if (i2 == 16) {
            return PorterDuff.Mode.DARKEN;
        }
        if (i2 == 17) {
            return PorterDuff.Mode.LIGHTEN;
        }
        if (i2 == 13) {
            return PorterDuff.Mode.MULTIPLY;
        }
        return PorterDuff.Mode.SRC_OVER;
    }

    public static String T(int i2) {
        if (i2 == 0) {
            return "Clear";
        }
        if (i2 == 1) {
            return "Src";
        }
        if (i2 == 2) {
            return "Dst";
        }
        if (i2 == 3) {
            return "SrcOver";
        }
        if (i2 == 4) {
            return "DstOver";
        }
        if (i2 == 5) {
            return "SrcIn";
        }
        if (i2 == 6) {
            return "DstIn";
        }
        if (i2 == 7) {
            return "SrcOut";
        }
        if (i2 == 8) {
            return "DstOut";
        }
        if (i2 == 9) {
            return "SrcAtop";
        }
        if (i2 == 10) {
            return "DstAtop";
        }
        if (i2 == 11) {
            return "Xor";
        }
        if (i2 == 12) {
            return "Plus";
        }
        if (i2 == 13) {
            return "Modulate";
        }
        if (i2 == 14) {
            return "Screen";
        }
        if (i2 == 15) {
            return "Overlay";
        }
        if (i2 == 16) {
            return "Darken";
        }
        if (i2 == 17) {
            return "Lighten";
        }
        if (i2 == 18) {
            return "ColorDodge";
        }
        if (i2 == 19) {
            return "ColorBurn";
        }
        if (i2 == 20) {
            return "HardLight";
        }
        if (i2 == 21) {
            return "Softlight";
        }
        if (i2 == 22) {
            return "Difference";
        }
        if (i2 == 23) {
            return "Exclusion";
        }
        if (i2 == 24) {
            return "Multiply";
        }
        if (i2 == 25) {
            return "Hue";
        }
        if (i2 == 26) {
            return "Saturation";
        }
        if (i2 == 27) {
            return "Color";
        }
        if (i2 == 28) {
            return "Luminosity";
        }
        return "Unknown";
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [gh, io0] */
    /* JADX WARN: Type inference failed for: r9v0, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r9v1, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5 */
    public static final ArrayList U(qw0 qw0Var, int i2, Integer num) {
        Object obj;
        ?? io0Var = new io0(qw0Var);
        int q = qw0Var.q(i2);
        wv a2 = qw0Var.a(i2);
        while (i2 >= 0) {
            if (qw0Var.k(i2)) {
                obj = qw0Var.p(qw0Var.b, i2);
            } else {
                obj = ph.a;
            }
            io0Var.h(qw0Var.i(i2), obj, qw0Var.a.f(i2), num);
            if (q >= 0) {
                wv wvVar = a2;
                a2 = qw0Var.a(q);
                i2 = q;
                q = qw0Var.q(q);
                num = wvVar;
            } else {
                i2 = q;
                num = a2;
            }
        }
        return (ArrayList) io0Var.a;
    }

    public static final void V(String str, long j2) {
        if (Build.VERSION.SDK_INT >= 29) {
            Trace.setCounter(str, j2);
        }
    }

    public static final v31 W(ij ijVar, yj yjVar, Object obj) {
        v31 v31Var = null;
        if ((ijVar instanceof jk) && yjVar.j(tc.g) != null) {
            jk jkVar = (jk) ijVar;
            while (true) {
                if ((jkVar instanceof jn) || (jkVar = jkVar.f()) == null) {
                    break;
                }
                if (jkVar instanceof v31) {
                    v31Var = (v31) jkVar;
                    break;
                }
            }
            if (v31Var != null) {
                v31Var.q0(yjVar, obj);
            }
        }
        return v31Var;
    }

    public static final cd0 X(u6 u6Var) {
        return new tm(u6Var, m);
    }

    public static final cd0 Y(u6 u6Var) {
        return new tm(u6Var, l);
    }

    public static final Object Z(yj yjVar, kv kvVar, sz0 sz0Var) {
        yj z;
        yj yjVar2 = sz0Var.f;
        yjVar2.getClass();
        if (!((Boolean) yjVar.n(new w4(6, (byte) 0), Boolean.FALSE)).booleanValue()) {
            z = yjVar2.i(yjVar);
        } else {
            z = z(yjVar2, yjVar, false);
        }
        g30.p(z);
        if (z == yjVar2) {
            ct0 ct0Var = new ct0(sz0Var, z);
            return o30.w(ct0Var, ct0Var, kvVar);
        }
        x1 x1Var = x1.A;
        if (o20.e(z.j(x1Var), yjVar2.j(x1Var))) {
            v31 v31Var = new v31(z, sz0Var);
            yj yjVar3 = v31Var.i;
            Object Q = k81.Q(yjVar3, null);
            try {
                return o30.w(v31Var, v31Var, kvVar);
            } finally {
                k81.G(yjVar3, Q);
            }
        }
        ct0 ct0Var2 = new ct0(sz0Var, z);
        try {
            n20.N(t20.w(t20.o(ct0Var2, ct0Var2, kvVar)), x31.a);
            AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = jn.k;
            do {
                int i2 = atomicIntegerFieldUpdater.get(ct0Var2);
                if (i2 != 0) {
                    if (i2 == 2) {
                        Object K = o20.K(ct0Var2.Q());
                        if (K instanceof qf) {
                            throw ((qf) K).a;
                        }
                        return K;
                    }
                    v7.o("Already suspended");
                    return null;
                }
            } while (!atomicIntegerFieldUpdater.compareAndSet(ct0Var2, 0, 1));
            return ik.e;
        } catch (Throwable th) {
            ct0Var2.u(new jq0(th));
            throw th;
        }
    }

    public static final void a(boolean z, vu vuVar, bw bwVar, int i2) {
        int i3;
        int i4;
        boolean z2;
        vuVar.getClass();
        bwVar.W(-940574400);
        if (bwVar.g(z)) {
            i3 = 4;
        } else {
            i3 = 2;
        }
        int i5 = i3 | i2;
        if (bwVar.h(vuVar)) {
            i4 = 32;
        } else {
            i4 = 16;
        }
        int i6 = i5 | i4;
        if ((i6 & 19) != 18) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (bwVar.O(i6 & 1, z2)) {
            k81.a(z, vuVar, bwVar, i6 & 126);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new i9(z, vuVar, i2);
        }
    }

    public static final void b(cd0 cd0Var, gg ggVar, bw bwVar, int i2, int i3) {
        cd0 cd0Var2;
        int i4;
        int i5;
        boolean z;
        cd0 cd0Var3;
        cd0 cd0Var4;
        dt0 dt0Var;
        boolean z2;
        zp zpVar;
        gl glVar;
        mm mmVar;
        zp zpVar2;
        gl glVar2;
        mm mmVar2;
        bw bwVar2 = bwVar;
        zr zrVar = k81.n;
        bwVar2.W(-830431939);
        int i6 = i3 & 1;
        int i7 = 2;
        if (i6 != 0) {
            i5 = i2 | 6;
            cd0Var2 = cd0Var;
        } else {
            cd0Var2 = cd0Var;
            if (bwVar2.f(cd0Var2)) {
                i4 = 4;
            } else {
                i4 = 2;
            }
            i5 = i4 | i2;
        }
        if ((i5 & 19) != 18) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar2.O(i5 & 1, z)) {
            zc0 zc0Var = zc0.a;
            if (i6 != 0) {
                cd0Var4 = zc0Var;
            } else {
                cd0Var4 = cd0Var2;
            }
            pc0 d2 = ya.d(x1.k);
            long j2 = bwVar2.T;
            int i8 = (int) (j2 ^ (j2 >>> 32));
            ll0 l2 = bwVar2.l();
            cd0 B = dl.B(bwVar2, zrVar);
            jh.c.getClass();
            di diVar = ih.b;
            bwVar2.Y();
            if (bwVar2.S) {
                bwVar2.k(diVar);
            } else {
                bwVar2.i0();
            }
            m20.F(ih.e, bwVar2, d2);
            m20.F(ih.d, bwVar2, l2);
            m20.F(ih.f, bwVar2, Integer.valueOf(i8));
            m20.C(ih.g, bwVar2);
            m20.F(ih.c, bwVar2, B);
            Object L = bwVar2.L();
            Object obj = ph.a;
            ij ijVar = null;
            if (L == obj) {
                L = n30.B(null);
                bwVar2.f0(L);
            }
            af0 af0Var = (af0) L;
            qy0 qy0Var = p4.b;
            Context context = (Context) bwVar2.j(qy0Var);
            dt0 dt0Var2 = new dt0(6);
            boolean h2 = bwVar2.h(context);
            Object L2 = bwVar2.L();
            if (h2 || L2 == obj) {
                L2 = new o6(i7, context, af0Var);
                bwVar2.f0(L2);
            }
            n30.D(dt0Var2, bwVar2);
            af0 D = n30.D((gv) L2, bwVar2);
            Object[] objArr = new Object[0];
            Object L3 = bwVar2.L();
            if (L3 == obj) {
                L3 = new c2(1);
                bwVar2.f0(L3);
            }
            String str = (String) y20.r(objArr, (vu) L3, bwVar2);
            i2 i2Var = (i2) bwVar2.j(na0.a);
            if (i2Var == null) {
                bwVar2.V(1213380307);
                Object obj2 = (Context) bwVar2.j(qy0Var);
                while (true) {
                    if (obj2 instanceof ContextWrapper) {
                        if (obj2 instanceof i2) {
                            break;
                        } else {
                            obj2 = ((ContextWrapper) obj2).getBaseContext();
                        }
                    } else {
                        obj2 = null;
                        break;
                    }
                }
                i2Var = (i2) obj2;
            } else {
                bwVar2.V(1213379439);
            }
            bwVar2.p(false);
            if (i2Var != null) {
                ag agVar = ((cg) i2Var).l;
                Object L4 = bwVar2.L();
                if (L4 == obj) {
                    L4 = new Object();
                    bwVar2.f0(L4);
                }
                b2 b2Var = (b2) L4;
                Object L5 = bwVar2.L();
                if (L5 == obj) {
                    L5 = new zb0(b2Var);
                    bwVar2.f0(L5);
                }
                zb0 zb0Var = (zb0) L5;
                boolean h3 = bwVar2.h(b2Var) | bwVar2.h(agVar) | bwVar2.f(str) | bwVar2.h(dt0Var2) | bwVar2.f(D);
                Object L6 = bwVar2.L();
                if (!h3 && L6 != obj) {
                    dt0Var = dt0Var2;
                } else {
                    L6 = new f2(b2Var, agVar, str, dt0Var2, D, 0);
                    dt0Var = dt0Var2;
                    bwVar2.f0(L6);
                }
                gv gvVar = (gv) L6;
                boolean f2 = bwVar2.f(dt0Var) | bwVar2.f(agVar) | bwVar2.f(str);
                Object L7 = bwVar2.L();
                if (f2 || L7 == obj) {
                    L7 = new rn(gvVar);
                    bwVar2.f0(L7);
                }
                c40 E = jc0.E(bwVar2);
                uj0 uj0Var = (uj0) af0Var.getValue();
                if (uj0Var == null) {
                    bwVar2.V(1392642692);
                    zp zpVar3 = (zp) yp.b.getValue();
                    a01 a01Var = fz.a;
                    zpVar3.getClass();
                    bwVar2.V(-1508925367);
                    ((dq0) bwVar2.j(eq0.b)).getClass();
                    cq0 a2 = dq0.a(bwVar2);
                    boolean f3 = bwVar2.f(zpVar3) | bwVar2.f(a2);
                    Object L8 = bwVar2.L();
                    if (f3 || L8 == obj) {
                        L8 = eq0.a(zpVar3, a2).a;
                        bwVar2.f0(L8);
                    }
                    String str2 = (String) L8;
                    if (bz0.z(str2, ".xml", true)) {
                        bwVar2.V(-1267601485);
                        qy0 qy0Var2 = gq0.a;
                        qy0Var2.getClass();
                        x4.a(bwVar2, 0);
                        gl glVar3 = (gl) bwVar2.j(qy0Var2);
                        mm mmVar3 = (mm) bwVar2.j(fi.h);
                        Object L9 = bwVar2.L();
                        if (L9 == obj) {
                            L9 = new c2(14);
                            bwVar2.f0(L9);
                        }
                        vu vuVar = (vu) L9;
                        boolean f4 = bwVar2.f(zpVar3) | bwVar2.h(glVar3) | bwVar2.f(mmVar3);
                        Object L10 = bwVar2.L();
                        if (!f4 && L10 != obj) {
                            zpVar2 = zpVar3;
                            glVar2 = glVar3;
                            mmVar2 = mmVar3;
                        } else {
                            zpVar2 = zpVar3;
                            glVar2 = glVar3;
                            mmVar2 = mmVar3;
                            L10 = new ez(zpVar2, glVar2, mmVar2, ijVar, 1);
                            bwVar2.f0(L10);
                        }
                        uj0Var = n30.E((iz) n30.C(zpVar2, glVar2, mmVar2, vuVar, (kv) L10, bwVar2).getValue(), bwVar2);
                        z2 = false;
                        bwVar2.p(false);
                    } else {
                        z2 = false;
                        if (bz0.z(str2, ".svg", true)) {
                            bwVar2.V(-1267490226);
                            qy0 qy0Var3 = gq0.a;
                            qy0Var3.getClass();
                            x4.a(bwVar2, 0);
                            gl glVar4 = (gl) bwVar2.j(qy0Var3);
                            mm mmVar4 = (mm) bwVar2.j(fi.h);
                            Object L11 = bwVar2.L();
                            if (L11 == obj) {
                                L11 = new c2(13);
                                bwVar2.f0(L11);
                            }
                            vu vuVar2 = (vu) L11;
                            boolean f5 = bwVar2.f(zpVar3) | bwVar2.h(glVar4) | bwVar2.f(mmVar4);
                            Object L12 = bwVar2.L();
                            if (!f5 && L12 != obj) {
                                zpVar = zpVar3;
                                glVar = glVar4;
                                mmVar = mmVar4;
                            } else {
                                zpVar = zpVar3;
                                glVar = glVar4;
                                mmVar = mmVar4;
                                L12 = new ez(zpVar, glVar, mmVar, ijVar, 0);
                                bwVar2.f0(L12);
                            }
                            uj0Var = (uj0) n30.C(zpVar, glVar, mmVar, vuVar2, (kv) L12, bwVar2).getValue();
                            z2 = false;
                            bwVar2.p(false);
                        } else {
                            bwVar2.V(-1267441060);
                            ca caVar = new ca(fz.b(zpVar3, bwVar2));
                            bwVar2.p(false);
                            bwVar2.p(false);
                            uj0Var = caVar;
                        }
                    }
                    bwVar2.p(z2);
                } else {
                    z2 = false;
                    bwVar2.V(1392642351);
                }
                bwVar2.p(z2);
                k81.d(uj0Var, H(zc0Var, E).b(cd0Var4).b(zrVar), null, dj.a, 0.0f, bwVar2, 24632);
                db dbVar = db.a;
                ggVar.h(dbVar, E, bwVar2, 390);
                boolean h4 = bwVar2.h(zb0Var);
                Object L13 = bwVar2.L();
                if (h4 || L13 == obj) {
                    L13 = new n9(0, zb0Var);
                    bwVar2.f0(L13);
                }
                cd0Var3 = cd0Var4;
                g30.c((vu) L13, E, dbVar.a(k81.z(jc0.K(dl.C(zc0Var, 16.0f), jc0.r), 56.0f), x1.n), false, f(4278225151L), 0L, ig.a, bwVar, 1597440, 40);
                bwVar2 = bwVar;
                bwVar2.p(true);
            } else {
                v7.o("No ActivityResultRegistryOwner was provided via LocalActivityResultRegistryOwner");
                return;
            }
        } else {
            bwVar2.R();
            cd0Var3 = cd0Var2;
        }
        mo0 r = bwVar2.r();
        if (r != null) {
            r.d = new o9(cd0Var3, ggVar, i2, i3);
        }
    }

    public static zb c(int i2, int i3, xb xbVar) {
        int i4 = i3 & 2;
        xb xbVar2 = xb.e;
        if (i4 != 0) {
            xbVar = xbVar2;
        }
        if (i2 != -2) {
            if (i2 != -1) {
                if (i2 != 0) {
                    if (i2 != Integer.MAX_VALUE) {
                        if (xbVar == xbVar2) {
                            return new zb(i2);
                        }
                        return new mi(i2, xbVar);
                    }
                    return new zb(Integer.MAX_VALUE);
                }
                if (xbVar == xbVar2) {
                    return new zb(0);
                }
                return new mi(1, xbVar);
            }
            if (xbVar == xbVar2) {
                return new mi(1, xb.f);
            }
            v7.m("CONFLATED capacity cannot be used with non-default onBufferOverflow");
            return null;
        }
        if (xbVar == xbVar2) {
            ed.b.getClass();
            return new zb(dd.b);
        }
        return new mi(1, xbVar);
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0170  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0177  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final long d(float r21, float r22, float r23, float r24, defpackage.we r25) {
        /*
            Method dump skipped, instructions count: 479
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.f31.d(float, float, float, float, we):long");
    }

    public static final long e(int i2) {
        long j2 = i2 << 32;
        int i3 = se.h;
        return j2;
    }

    public static final long f(long j2) {
        long j3 = j2 << 32;
        int i2 = se.h;
        return j3;
    }

    public static o5 g(int i2, int i3, int i4, int i5) {
        Bitmap createBitmap;
        if ((i5 & 4) != 0) {
            i4 = 0;
        }
        wq0 wq0Var = af.e;
        Bitmap.Config Q = Q(i4);
        if (Build.VERSION.SDK_INT >= 26) {
            createBitmap = Bitmap.createBitmap((DisplayMetrics) null, i2, i3, Q(i4), true, ye.a(wq0Var));
        } else {
            createBitmap = Bitmap.createBitmap((DisplayMetrics) null, i2, i3, Q);
            createBitmap.setHasAlpha(true);
        }
        return new o5(createBitmap);
    }

    public static final long h(long j2, long j3) {
        if (j2 != 4611686018427387903L && j2 != -4611686018427387903L) {
            if (j3 != 4611686018427387903L && j3 != -4611686018427387903L) {
                return n30.k(j2 + j3);
            }
            return j3;
        }
        if ((-4611686018427387903L < j3 && j3 < 4611686018427387903L) || (j3 ^ j2) >= 0) {
            return j2;
        }
        return 9223372036854759646L;
    }

    public static final void i(List list, int i2, int i3) {
        int v = v(i2, list);
        if (v < 0) {
            v = -(v + 1);
        }
        while (v < list.size() && ((v20) list.get(v)).b < i3) {
        }
    }

    public static final Bitmap j(o5 o5Var) {
        if (o5Var instanceof o5) {
            return o5Var.a;
        }
        throw new UnsupportedOperationException("Unable to obtain android.graphics.Bitmap");
    }

    public static Map k(Object obj) {
        if ((obj instanceof q30) && !(obj instanceof r30)) {
            N(obj, "kotlin.collections.MutableMap");
            throw null;
        }
        try {
            return (Map) obj;
        } catch (ClassCastException e2) {
            o20.A(e2, f31.class.getName());
            throw e2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x005c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x005a -> B:10:0x005d). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object l(defpackage.xz0 r7, defpackage.qm0 r8, defpackage.s9 r9) {
        /*
            boolean r0 = r9 instanceof defpackage.ru
            if (r0 == 0) goto L13
            r0 = r9
            ru r0 = (defpackage.ru) r0
            int r1 = r0.k
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.k = r1
            goto L18
        L13:
            ru r0 = new ru
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.j
            int r1 = r0.k
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L34
            if (r1 != r3) goto L2d
            qm0 r7 = r0.i
            xz0 r8 = r0.h
            defpackage.o30.x(r9)
            r6 = r8
            r8 = r7
            r7 = r6
            goto L5d
        L2d:
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r7)
            r7 = 0
            return r7
        L34:
            defpackage.o30.x(r9)
            yz0 r9 = r7.j
            pm0 r9 = r9.w
            java.util.List r9 = r9.a
            int r1 = r9.size()
            r4 = r2
        L42:
            if (r4 >= r1) goto L79
            java.lang.Object r5 = r9.get(r4)
            um0 r5 = (defpackage.um0) r5
            boolean r5 = r5.d
            if (r5 == 0) goto L76
        L4e:
            r0.h = r7
            r0.i = r8
            r0.k = r3
            java.lang.Object r9 = r7.A(r8, r0)
            ik r1 = defpackage.ik.e
            if (r9 != r1) goto L5d
            return r1
        L5d:
            pm0 r9 = (defpackage.pm0) r9
            java.util.List r9 = r9.a
            int r1 = r9.size()
            r4 = r2
        L66:
            if (r4 >= r1) goto L79
            java.lang.Object r5 = r9.get(r4)
            um0 r5 = (defpackage.um0) r5
            boolean r5 = r5.d
            if (r5 == 0) goto L73
            goto L4e
        L73:
            int r4 = r4 + 1
            goto L66
        L76:
            int r4 = r4 + 1
            goto L42
        L79:
            x31 r7 = defpackage.x31.a
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.f31.l(xz0, qm0, s9):java.lang.Object");
    }

    public static final Object m(ym0 ym0Var, kv kvVar, ij ijVar) {
        ik ikVar;
        x31 x31Var;
        su suVar = new su(ijVar.r(), kvVar, null, 0);
        yz0 yz0Var = (yz0) ym0Var;
        yz0Var.getClass();
        pc pcVar = new pc(1, t20.w(ijVar));
        pcVar.s();
        xz0 xz0Var = new xz0(yz0Var, pcVar);
        synchronized (yz0Var.y) {
            yz0Var.x.b(xz0Var);
            ij w = t20.w(t20.o(xz0Var, xz0Var, suVar));
            ikVar = ik.e;
            wr0 wr0Var = new wr0(w);
            x31Var = x31.a;
            wr0Var.u(x31Var);
        }
        pcVar.w(new q2(24, xz0Var));
        Object p = pcVar.p();
        if (p == ikVar) {
            return p;
        }
        return x31Var;
    }

    public static void n(int i2, Object obj) {
        if (obj != null && !D(i2, obj)) {
            N(obj, "kotlin.jvm.functions.Function" + i2);
            throw null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [gh, io0] */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v2, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v3, types: [wv] */
    /* JADX WARN: Type inference failed for: r6v7, types: [java.lang.Integer] */
    public static final List o(uw0 uw0Var, Integer num, int i2, Integer num2) {
        int i3;
        int i4;
        int s;
        Object obj;
        int i5;
        pe0 pe0Var;
        if (!uw0Var.w && uw0Var.p() != 0) {
            ?? io0Var = new io0(uw0Var);
            if (num2 != null) {
                i3 = num2.intValue();
            } else {
                i3 = uw0Var.v;
                if (i3 < 0) {
                    i3 = uw0Var.E(uw0Var.b, i2);
                }
            }
            if (num == 0) {
                int N = uw0Var.i - uw0Var.N(uw0Var.b, uw0Var.r(i2));
                he0 he0Var = uw0Var.s;
                if (he0Var != null && (pe0Var = (pe0) he0Var.b(i2)) != null) {
                    i5 = pe0Var.b;
                } else {
                    i5 = 0;
                }
                num = Integer.valueOf(N + i5);
            }
            int r = uw0Var.r(i2) * 5;
            int[] iArr = uw0Var.b;
            if (r < iArr.length) {
                s = uw0Var.s(i2);
            } else {
                if (i3 >= 0) {
                    i4 = uw0Var.E(iArr, i3);
                } else {
                    i4 = i3;
                }
                s = uw0Var.s(i3);
                int i6 = i3;
                i3 = i4;
                i2 = i6;
            }
            while (i2 >= 0) {
                if ((uw0Var.b[(uw0Var.r(i2) * 5) + 1] & 536870912) != 0) {
                    obj = uw0Var.t(i2);
                } else {
                    obj = ph.a;
                }
                io0Var.h(s, obj, uw0Var.O(i2), num);
                num = uw0Var.b(i2);
                if (i3 >= 0) {
                    int E = uw0Var.E(uw0Var.b, i3);
                    s = uw0Var.s(i3);
                    int i7 = i3;
                    i3 = E;
                    i2 = i7;
                } else {
                    i2 = i3;
                }
            }
            return (ArrayList) io0Var.a;
        }
        return er.e;
    }

    public static final void p(qw0 qw0Var, ArrayList arrayList, int i2) {
        boolean l2 = qw0Var.l(i2);
        int[] iArr = qw0Var.b;
        if (l2) {
            arrayList.add(qw0Var.n(i2));
            return;
        }
        int i3 = iArr[(i2 * 5) + 3] + i2;
        for (int i4 = i2 + 1; i4 < i3; i4 += iArr[(i4 * 5) + 3]) {
            p(qw0Var, arrayList, i4);
        }
    }

    public static final int q(long j2, long j3) {
        boolean F = F(j2);
        if (F != F(j3)) {
            if (!F) {
                return 1;
            }
            return -1;
        }
        int signum = (int) Math.signum(B(j2) - B(j3));
        if (Math.min(B(j2), B(j3)) >= 0.0f && E(j2) != E(j3)) {
            if (!E(j2)) {
                return 1;
            }
            return -1;
        }
        return signum;
    }

    public static final Object r(long j2, jj jjVar) {
        if (j2 > 0) {
            pc pcVar = new pc(1, t20.w(jjVar));
            pcVar.s();
            if (j2 < Long.MAX_VALUE) {
                A(pcVar.i).f(j2, pcVar);
            }
            Object p = pcVar.p();
            if (p == ik.e) {
                return p;
            }
        }
        return x31.a;
    }

    public static cd0 s(cd0 cd0Var, m9 m9Var, vu vuVar, gv gvVar, vu vuVar2, vu vuVar3, vu vuVar4, gv gvVar2, c40 c40Var, kv kvVar, gv gvVar3, int i2) {
        vu vuVar5;
        vu vuVar6;
        vu vuVar7;
        gv gvVar4;
        c40 c40Var2;
        kv kvVar2;
        cd0 cd0Var2;
        cd0 cd0Var3;
        cd0 cd0Var4;
        if ((i2 & 8) != 0) {
            vuVar5 = b;
        } else {
            vuVar5 = vuVar2;
        }
        if ((i2 & 16) != 0) {
            vuVar6 = c;
        } else {
            vuVar6 = vuVar3;
        }
        gv gvVar5 = null;
        if ((i2 & 32) != 0) {
            vuVar7 = null;
        } else {
            vuVar7 = vuVar4;
        }
        if ((i2 & 64) != 0) {
            gvVar4 = null;
        } else {
            gvVar4 = gvVar2;
        }
        if ((i2 & 128) != 0) {
            c40Var2 = null;
        } else {
            c40Var2 = c40Var;
        }
        if ((i2 & 512) != 0) {
            kvVar2 = d;
        } else {
            kvVar2 = kvVar;
        }
        if ((i2 & 1024) == 0) {
            gvVar5 = gvVar3;
        }
        cd0Var.getClass();
        m9Var.getClass();
        vuVar.getClass();
        gvVar.getClass();
        kvVar2.getClass();
        bw0 bw0Var = new bw0(vuVar);
        cd0 cd0Var5 = zc0.a;
        if (gvVar4 != null) {
            cd0Var2 = k81.w(cd0Var5, gvVar4);
        } else {
            cd0Var2 = cd0Var5;
        }
        cd0 b2 = cd0Var.b(cd0Var2);
        if (vuVar7 != null) {
            cd0Var3 = new z00(bw0Var, vuVar7);
        } else {
            cd0Var3 = cd0Var5;
        }
        cd0 b3 = b2.b(cd0Var3);
        if (vuVar6 != null) {
            cd0Var4 = new uv0(bw0Var, vuVar6);
        } else {
            cd0Var4 = cd0Var5;
        }
        cd0 b4 = b3.b(cd0Var4);
        if (vuVar5 != null) {
            cd0Var5 = new ey(bw0Var, vuVar5);
        }
        return b4.b(cd0Var5).b(new lp(m9Var, bw0Var, gvVar, gvVar4, c40Var2, kvVar2, gvVar5));
    }

    public static cd0 t(cd0 cd0Var, c40 c40Var, vu vuVar, gv gvVar, kv kvVar, int i2) {
        if ((i2 & 64) != 0) {
            kvVar = d;
        }
        kv kvVar2 = kvVar;
        cd0Var.getClass();
        c40Var.getClass();
        vuVar.getClass();
        gvVar.getClass();
        kvVar2.getClass();
        return cd0Var.b(zc0.a).b(new lp(c40Var, new bw0(vuVar), gvVar, null, null, kvVar2, null));
    }

    public static final long u(long j2) {
        long j3 = (j2 << 1) + 1;
        aq.e.getClass();
        int i2 = bq.a;
        return j3;
    }

    public static final int v(int i2, List list) {
        int size = list.size() - 1;
        int i3 = 0;
        while (i3 <= size) {
            int i4 = (i3 + size) >>> 1;
            int i5 = o20.i(((v20) list.get(i4)).b, i2);
            if (i5 < 0) {
                i3 = i4 + 1;
            } else if (i5 > 0) {
                size = i4 - 1;
            } else {
                return i4;
            }
        }
        return -(i3 + 1);
    }

    public static final Integer w(qw0 qw0Var, th thVar, int i2, int i3) {
        Integer w;
        gw gwVar;
        Object obj;
        int[] iArr = qw0Var.b;
        while (true) {
            yv yvVar = null;
            if (i2 >= i3) {
                return null;
            }
            int i4 = iArr[(i2 * 5) + 3] + i2;
            if (qw0Var.j(i2) && qw0Var.i(i2) == 206 && o20.e(qw0Var.p(iArr, i2), rh.e)) {
                Object h2 = qw0Var.h(i2, 0);
                if (h2 instanceof gw) {
                    gwVar = (gw) h2;
                } else {
                    gwVar = null;
                }
                if (gwVar != null) {
                    obj = gwVar.a;
                } else {
                    obj = null;
                }
                if (obj instanceof yv) {
                    yvVar = (yv) obj;
                }
                if (yvVar != null && yvVar.e == thVar) {
                    return Integer.valueOf(i2);
                }
            }
            if (qw0Var.d(i2) && (w = w(qw0Var, thVar, i2 + 1, i4)) != null) {
                return Integer.valueOf(w.intValue());
            }
            i2 = i4;
        }
    }

    public static long x(int i2, int i3, int i4, int i5) {
        int min;
        int i6;
        int i7 = 262142;
        int min2 = Math.min(i4, 262142);
        int i8 = Integer.MAX_VALUE;
        if (i5 == Integer.MAX_VALUE) {
            min = Integer.MAX_VALUE;
        } else {
            min = Math.min(i5, 262142);
        }
        if (min == Integer.MAX_VALUE) {
            i6 = min2;
        } else {
            i6 = min;
        }
        if (i6 >= 8191) {
            if (i6 < 32767) {
                i7 = 65534;
            } else if (i6 < 65535) {
                i7 = 32766;
            } else if (i6 < 262143) {
                i7 = 8190;
            } else {
                ti.j(i6);
                throw new RuntimeException();
            }
        }
        if (i3 != Integer.MAX_VALUE) {
            i8 = Math.min(i7, i3);
        }
        return ti.a(Math.min(i7, i2), i8, min2, min);
    }

    public static long y(int i2, int i3, int i4, int i5) {
        int min;
        int i6;
        int i7 = 262142;
        int min2 = Math.min(i2, 262142);
        int i8 = Integer.MAX_VALUE;
        if (i3 == Integer.MAX_VALUE) {
            min = Integer.MAX_VALUE;
        } else {
            min = Math.min(i3, 262142);
        }
        if (min == Integer.MAX_VALUE) {
            i6 = min2;
        } else {
            i6 = min;
        }
        if (i6 >= 8191) {
            if (i6 < 32767) {
                i7 = 65534;
            } else if (i6 < 65535) {
                i7 = 32766;
            } else if (i6 < 262143) {
                i7 = 8190;
            } else {
                ti.j(i6);
                throw new RuntimeException();
            }
        }
        if (i5 != Integer.MAX_VALUE) {
            i8 = Math.min(i7, i5);
        }
        return ti.a(min2, min, Math.min(i7, i4), i8);
    }

    public static final yj z(yj yjVar, yj yjVar2, boolean z) {
        Boolean bool = Boolean.FALSE;
        int i2 = 6;
        byte b2 = 0;
        boolean booleanValue = ((Boolean) yjVar.n(new w4(i2, b2), bool)).booleanValue();
        boolean booleanValue2 = ((Boolean) yjVar2.n(new w4(i2, b2), bool)).booleanValue();
        if (!booleanValue && !booleanValue2) {
            return yjVar.i(yjVar2);
        }
        w4 w4Var = new w4(4, b2);
        cr crVar = cr.e;
        yj yjVar3 = (yj) yjVar.n(w4Var, crVar);
        Object obj = yjVar2;
        if (booleanValue2) {
            obj = yjVar2.n(new w4(5, b2), crVar);
        }
        return yjVar3.i((yj) obj);
    }
}
