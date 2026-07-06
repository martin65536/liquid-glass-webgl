package defpackage;

import android.os.Build;
import android.view.View;
import android.view.contentcapture.ContentCaptureSession;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class s3 extends uv implements vu {
    public final /* synthetic */ int l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ s3(int i, Object obj, Class cls, String str, String str2, int i2, int i3, int i4) {
        super(i, obj, cls, str, str2, i2, i3);
        this.l = i4;
    }

    @Override // defpackage.vu
    public final Object a() {
        ContentCaptureSession a;
        lg0 lg0Var;
        int i = this.l;
        Object obj = this.f;
        switch (i) {
            case 0:
                View view = (View) obj;
                int i2 = Build.VERSION.SDK_INT;
                if (i2 >= 30) {
                    f1.f(view);
                }
                if (i2 < 29 || (a = xi.a(view)) == null) {
                    return null;
                }
                return new c4(4, a, view);
            case 1:
                ft ftVar = (ft) obj;
                we0 we0Var = ftVar.c;
                we0 we0Var2 = ftVar.d;
                lt ltVar = ftVar.a;
                pt f = ltVar.f();
                long j = -9187201950435737472L;
                if (f == null) {
                    Object[] objArr = we0Var2.b;
                    long[] jArr = we0Var2.a;
                    int length = jArr.length - 2;
                    if (length >= 0) {
                        int i3 = 0;
                        while (true) {
                            long j2 = jArr[i3];
                            if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                                int i4 = 8 - ((~(i3 - length)) >>> 31);
                                for (int i5 = 0; i5 < i4; i5++) {
                                    if ((j2 & 255) >= 128) {
                                        j2 >>= 8;
                                    } else {
                                        ((r9) objArr[(i3 << 3) + i5]).E0();
                                        throw null;
                                    }
                                }
                                if (i4 != 8) {
                                }
                            }
                            if (i3 != length) {
                                i3++;
                            }
                        }
                    }
                } else if (f.r) {
                    if (we0Var.c(f)) {
                        f.J0();
                    }
                    f.I0();
                    if (!f.e.r) {
                        q00.b("visitAncestors called on an unattached node");
                    }
                    bd0 bd0Var = f.e;
                    z40 E = k81.E(f);
                    int i6 = 0;
                    while (E != null) {
                        if ((E.H.f.h & 5120) != 0) {
                            while (bd0Var != null) {
                                int i7 = bd0Var.g;
                                if ((i7 & 5120) != 0) {
                                    if ((i7 & 1024) != 0) {
                                        i6++;
                                    }
                                    if ((bd0Var instanceof r9) && we0Var2.c(bd0Var)) {
                                        if (i6 <= 1) {
                                            ((r9) bd0Var).E0();
                                            throw null;
                                        }
                                        ((r9) bd0Var).E0();
                                        throw null;
                                    }
                                }
                                bd0Var = bd0Var.i;
                            }
                        }
                        E = E.s();
                        if (E != null && (lg0Var = E.H) != null) {
                            bd0Var = lg0Var.e;
                        } else {
                            bd0Var = null;
                        }
                    }
                    Object[] objArr2 = we0Var2.b;
                    long[] jArr2 = we0Var2.a;
                    int length2 = jArr2.length - 2;
                    if (length2 >= 0) {
                        int i8 = 0;
                        while (true) {
                            long j3 = jArr2[i8];
                            long j4 = j;
                            if ((((~j3) << 7) & j3 & j4) != j4) {
                                int i9 = 8 - ((~(i8 - length2)) >>> 31);
                                for (int i10 = 0; i10 < i9; i10++) {
                                    if ((j3 & 255) >= 128) {
                                        j3 >>= 8;
                                    } else {
                                        ((r9) objArr2[(i8 << 3) + i10]).E0();
                                        throw null;
                                    }
                                }
                                if (i9 != 8) {
                                }
                            }
                            if (i8 != length2) {
                                i8++;
                                j = j4;
                            }
                        }
                    }
                }
                if (ltVar.f() == null || ltVar.c.I0() == ot.g) {
                    ltVar.c();
                }
                we0Var.b();
                we0Var2.b();
                ftVar.e = false;
                return x31.a;
            default:
                return Boolean.valueOf(((tt) obj).z.K0(7));
        }
    }
}
