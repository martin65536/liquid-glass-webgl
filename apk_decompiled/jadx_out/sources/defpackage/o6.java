package defpackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Looper;
import android.view.Choreographer;
import android.view.View;
import java.io.InputStream;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class o6 extends z30 implements gv {
    public final /* synthetic */ int f;
    public final /* synthetic */ Object g;
    public final /* synthetic */ Object h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ o6(int i, Object obj, Object obj2) {
        super(1);
        this.f = i;
        this.h = obj;
        this.g = obj2;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        o5 o5Var;
        float f;
        switch (this.f) {
            case 0:
                n6 n6Var = (n6) this.h;
                p6 p6Var = (p6) this.g;
                synchronized (n6Var.i) {
                    n6Var.k.remove(p6Var);
                }
                return x31.a;
            case 1:
                ((Choreographer) ((q6) this.h).f).removeFrameCallback((p6) this.g);
                return x31.a;
            case 2:
                Uri uri = (Uri) obj;
                if (uri != null) {
                    try {
                        InputStream openInputStream = ((Context) this.h).getContentResolver().openInputStream(uri);
                        if (openInputStream != null) {
                            af0 af0Var = (af0) this.g;
                            try {
                                Bitmap decodeStream = BitmapFactory.decodeStream(openInputStream);
                                if (decodeStream != null) {
                                    o5Var = new o5(decodeStream);
                                } else {
                                    o5Var = null;
                                }
                                if (o5Var != null) {
                                    af0Var.setValue(new ca(o5Var));
                                }
                                openInputStream.close();
                            } catch (Throwable th) {
                                try {
                                    throw th;
                                } finally {
                                }
                            }
                        }
                    } catch (Exception unused) {
                    }
                }
                return x31.a;
            case 3:
                dm0.H((dm0) obj, (em0) this.h, ((fa) this.g).s);
                return x31.a;
            case 4:
                lx lxVar = (lx) obj;
                lxVar.getClass();
                float floatValue = ((Number) ((hy0) this.g).getValue()).floatValue();
                float floatValue2 = ((Number) ((y6) this.h).d()).floatValue();
                lxVar.g((1.0f - floatValue) * (-lxVar.G(48.0f)));
                lxVar.b(fq.b.b(floatValue2));
                float c = lxVar.c();
                float f2 = floatValue - 1.0f;
                if (f2 < 0.0f) {
                    f = 0.0f;
                } else {
                    f = f2;
                }
                lxVar.i(c / ((f * 0.1f) + 1.0f));
                float t = lxVar.t();
                if (f2 < 0.0f) {
                    f2 = 0.0f;
                }
                lxVar.q(((f2 * 0.1f) + 1.0f) * t);
                return x31.a;
            case 5:
                up upVar = (up) obj;
                yc ycVar = (yc) this.h;
                mm s = upVar.J().s();
                m40 u = upVar.J().u();
                uc q = upVar.J().q();
                long v = upVar.J().v();
                hx hxVar = (hx) upVar.J().g;
                gv gvVar = (gv) this.g;
                r7 r7Var = ycVar.f;
                mm s2 = r7Var.s();
                m40 u2 = r7Var.u();
                uc q2 = r7Var.q();
                long v2 = r7Var.v();
                hx hxVar2 = (hx) r7Var.g;
                r7Var.E(s);
                r7Var.F(u);
                r7Var.D(q);
                r7Var.G(v);
                r7Var.g = hxVar;
                q.h();
                try {
                    gvVar.e(ycVar);
                    q.f();
                    r7Var.E(s2);
                    r7Var.F(u2);
                    r7Var.D(q2);
                    r7Var.G(v2);
                    r7Var.g = hxVar2;
                    return x31.a;
                } catch (Throwable th2) {
                    q.f();
                    r7Var.E(s2);
                    r7Var.F(u2);
                    r7Var.D(q2);
                    r7Var.G(v2);
                    r7Var.g = hxVar2;
                    throw th2;
                }
            case 6:
                al alVar = (al) obj;
                alVar.getClass();
                if (((Boolean) ((af0) this.g).getValue()).booleanValue()) {
                    ((gv) this.h).e(Float.valueOf(alVar.d()));
                }
                return x31.a;
            case 7:
                dm0.H((dm0) obj, (em0) this.h, ((kw0) this.g).D);
                return x31.a;
            default:
                nh nhVar = (nh) obj;
                kv kvVar = (kv) this.g;
                f81 f81Var = (f81) this.h;
                if (!f81Var.g) {
                    j80 j80Var = nhVar.c;
                    View view = nhVar.a;
                    l80 f3 = j80Var.f();
                    f81Var.i = kvVar;
                    if (f81Var.h == null) {
                        if (!o20.e(Looper.myLooper(), view.getHandler().getLooper())) {
                            view.post(new r4(2, f81Var, f3));
                        } else {
                            f81Var.h = f3;
                            f3.a(f81Var);
                        }
                    } else if (f3.c.compareTo(a80.g) >= 0) {
                        f81Var.f.A(new gg(-1723985096, true, new ei(f81Var, nhVar, kvVar)));
                    }
                }
                return x31.a;
        }
    }
}
