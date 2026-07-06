package defpackage;

import android.content.pm.PackageInfo;
import android.graphics.Paint;
import android.os.Build;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import com.kyant.backdrop.catalog.R;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Set;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class o4 {
    public static final wq b;
    public static final wq f;
    public static final long i = Long.MIN_VALUE;
    public static final /* synthetic */ int j = 0;
    public static final ij[] a = new ij[0];
    public static final int[] c = new int[0];
    public static final long[] d = new long[0];
    public static final Object[] e = new Object[0];
    public static final rt g = new rt(20);
    public static final Object h = new Object();

    static {
        int i2 = 1;
        b = new wq("RESUME_TOKEN", i2);
        f = new wq("NULL", i2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0085, code lost:
    
        if (r10 == r5) goto L32;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0073 A[Catch: all -> 0x0036, TRY_LEAVE, TryCatch #1 {all -> 0x0036, blocks: (B:12:0x002f, B:14:0x0057, B:20:0x006b, B:22:0x0073, B:32:0x0046, B:34:0x004d), top: B:7:0x0021 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    /* JADX WARN: Type inference failed for: r0v2, types: [jj, ij, qs] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v2, types: [ps] */
    /* JADX WARN: Type inference failed for: r1v3, types: [yb] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r8v5, types: [ed] */
    /* JADX WARN: Type inference failed for: r8v6, types: [ed] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0085 -> B:13:0x0032). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object A(defpackage.ps r7, defpackage.un0 r8, boolean r9, defpackage.jj r10) {
        /*
            boolean r0 = r10 instanceof defpackage.qs
            if (r0 == 0) goto L13
            r0 = r10
            qs r0 = (defpackage.qs) r0
            int r1 = r0.m
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.m = r1
            goto L18
        L13:
            qs r0 = new qs
            r0.<init>(r10)
        L18:
            java.lang.Object r10 = r0.l
            int r1 = r0.m
            r2 = 0
            r3 = 2
            r4 = 1
            ik r5 = defpackage.ik.e
            if (r1 == 0) goto L4a
            if (r1 == r4) goto L3e
            if (r1 != r3) goto L38
            boolean r9 = r0.k
            yb r7 = r0.j
            ed r8 = r0.i
            ps r1 = r0.h
            defpackage.o30.x(r10)     // Catch: java.lang.Throwable -> L36
        L32:
            r6 = r1
            r1 = r7
            r7 = r6
            goto L57
        L36:
            r7 = move-exception
            goto L90
        L38:
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r7)
            return r2
        L3e:
            boolean r9 = r0.k
            yb r7 = r0.j
            ed r8 = r0.i
            ps r1 = r0.h
            defpackage.o30.x(r10)     // Catch: java.lang.Throwable -> L36
            goto L6b
        L4a:
            defpackage.o30.x(r10)
            zb r10 = r8.j     // Catch: java.lang.Throwable -> L36
            r10.getClass()     // Catch: java.lang.Throwable -> L36
            yb r1 = new yb     // Catch: java.lang.Throwable -> L36
            r1.<init>(r10)     // Catch: java.lang.Throwable -> L36
        L57:
            r0.h = r7     // Catch: java.lang.Throwable -> L36
            r0.i = r8     // Catch: java.lang.Throwable -> L36
            r0.j = r1     // Catch: java.lang.Throwable -> L36
            r0.k = r9     // Catch: java.lang.Throwable -> L36
            r0.m = r4     // Catch: java.lang.Throwable -> L36
            java.lang.Object r10 = r1.b(r0)     // Catch: java.lang.Throwable -> L36
            if (r10 != r5) goto L68
            goto L87
        L68:
            r6 = r1
            r1 = r7
            r7 = r6
        L6b:
            java.lang.Boolean r10 = (java.lang.Boolean) r10     // Catch: java.lang.Throwable -> L36
            boolean r10 = r10.booleanValue()     // Catch: java.lang.Throwable -> L36
            if (r10 == 0) goto L88
            java.lang.Object r10 = r7.c()     // Catch: java.lang.Throwable -> L36
            r0.h = r1     // Catch: java.lang.Throwable -> L36
            r0.i = r8     // Catch: java.lang.Throwable -> L36
            r0.j = r7     // Catch: java.lang.Throwable -> L36
            r0.k = r9     // Catch: java.lang.Throwable -> L36
            r0.m = r3     // Catch: java.lang.Throwable -> L36
            java.lang.Object r10 = r1.g(r10, r0)     // Catch: java.lang.Throwable -> L36
            if (r10 != r5) goto L32
        L87:
            return r5
        L88:
            if (r9 == 0) goto L8d
            r8.a(r2)
        L8d:
            x31 r7 = defpackage.x31.a
            return r7
        L90:
            throw r7     // Catch: java.lang.Throwable -> L91
        L91:
            r10 = move-exception
            if (r9 == 0) goto Laa
            boolean r9 = r7 instanceof java.util.concurrent.CancellationException
            if (r9 == 0) goto L9b
            r2 = r7
            java.util.concurrent.CancellationException r2 = (java.util.concurrent.CancellationException) r2
        L9b:
            if (r2 != 0) goto La7
            java.util.concurrent.CancellationException r2 = new java.util.concurrent.CancellationException
            java.lang.String r9 = "Channel was consumed, consumer had failed"
            r2.<init>(r9)
            r2.initCause(r7)
        La7:
            r8.a(r2)
        Laa:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.o4.A(ps, un0, boolean, jj):java.lang.Object");
    }

    public static final int B(View view, int i2) {
        int i3 = 0;
        int i4 = Integer.MAX_VALUE;
        Object obj = null;
        while (view != null) {
            Object tag = view.getTag(i2);
            if (tag != null) {
                if (obj == null) {
                    obj = tag;
                } else if (!tag.equals(obj)) {
                    break;
                }
                i4 = i3;
            }
            i3++;
            Object j2 = y20.j(view);
            if (j2 instanceof View) {
                view = (View) j2;
            } else {
                view = null;
            }
        }
        return i4;
    }

    public static final View C(View view) {
        View view2;
        if (view.isAttachedToWindow()) {
            int min = Math.min(B(view, R.id.view_tree_lifecycle_owner), B(view, R.id.view_tree_saved_state_registry_owner));
            View view3 = view;
            int i2 = 0;
            View view4 = view3;
            while (view != null) {
                if (i2 == min) {
                    if (!(view.getParent() instanceof ViewGroup)) {
                        return view3;
                    }
                } else if (D(view) == null) {
                    i2++;
                    Object j2 = y20.j(view);
                    if (j2 instanceof View) {
                        view2 = (View) j2;
                    } else {
                        view2 = null;
                    }
                    View view5 = view3;
                    view3 = view;
                    view = view2;
                    view4 = view5;
                }
                return view;
            }
            return view4;
        }
        return view;
    }

    public static final nh D(View view) {
        WeakReference weakReference;
        Object tag = view.getTag(R.id.androidx_compose_ui_view_compose_view_context);
        if (tag instanceof WeakReference) {
            weakReference = (WeakReference) tag;
        } else {
            weakReference = null;
        }
        if (weakReference == null) {
            return null;
        }
        return (nh) weakReference.get();
    }

    public static final String[] E(fj fjVar) {
        fjVar.getClass();
        return (String[]) ((v4) fjVar).b.toArray(new String[0]);
    }

    public static final float F(Layout layout, int i2, Paint paint) {
        int i3;
        float abs;
        float width;
        float lineLeft = layout.getLineLeft(i2);
        ThreadLocal threadLocal = i11.a;
        if (layout.getEllipsisCount(i2) <= 0 || layout.getParagraphDirection(i2) != 1 || lineLeft >= 0.0f) {
            return 0.0f;
        }
        float measureText = paint.measureText("…") + (layout.getPrimaryHorizontal(layout.getEllipsisStart(i2) + layout.getLineStart(i2)) - lineLeft);
        Layout.Alignment paragraphAlignment = layout.getParagraphAlignment(i2);
        if (paragraphAlignment == null) {
            i3 = -1;
        } else {
            i3 = vz.a[paragraphAlignment.ordinal()];
        }
        if (i3 == 1) {
            abs = Math.abs(lineLeft);
            width = (layout.getWidth() - measureText) / 2.0f;
        } else {
            abs = Math.abs(lineLeft);
            width = layout.getWidth() - measureText;
        }
        return width + abs;
    }

    public static final float G(Layout layout, int i2, Paint paint) {
        float width;
        float width2;
        ThreadLocal threadLocal = i11.a;
        if (layout.getEllipsisCount(i2) > 0) {
            int i3 = -1;
            if (layout.getParagraphDirection(i2) == -1 && layout.getWidth() < layout.getLineRight(i2)) {
                float measureText = paint.measureText("…") + (layout.getLineRight(i2) - layout.getPrimaryHorizontal(layout.getEllipsisStart(i2) + layout.getLineStart(i2)));
                Layout.Alignment paragraphAlignment = layout.getParagraphAlignment(i2);
                if (paragraphAlignment != null) {
                    i3 = vz.a[paragraphAlignment.ordinal()];
                }
                if (i3 == 1) {
                    width = layout.getWidth() - layout.getLineRight(i2);
                    width2 = (layout.getWidth() - measureText) / 2.0f;
                } else {
                    width = layout.getWidth() - layout.getLineRight(i2);
                    width2 = layout.getWidth() - measureText;
                }
                return width - width2;
            }
            return 0.0f;
        }
        return 0.0f;
    }

    public static final Paint H(r5 r5Var) {
        if (!(r5Var instanceof r5)) {
            p00.a("Extracting native reference is only supported from androidx.compose.ui.graphics.AndroidPaint instances but received " + fp0.a(r5Var.getClass()).a());
        }
        return r5Var.a;
    }

    public static final my0 I(mx0 mx0Var) {
        my0 my0Var = mx0Var.e;
        my0Var.getClass();
        return (my0) cx0.u(my0Var, mx0Var);
    }

    public static final int J(mx0 mx0Var) {
        my0 my0Var = mx0Var.e;
        my0Var.getClass();
        return ((my0) cx0.h(my0Var)).e;
    }

    public static final void K(yj yjVar, Throwable th) {
        try {
            bk bkVar = (bk) yjVar.j(x1.B);
            if (bkVar != null) {
                bkVar.l(yjVar, th);
            } else {
                k81.y(yjVar, th);
            }
        } catch (Throwable th2) {
            if (th != th2) {
                RuntimeException runtimeException = new RuntimeException("Exception while trying to handle coroutine exception", th2);
                o20.d(runtimeException, th);
                th = runtimeException;
            }
            k81.y(yjVar, th);
        }
    }

    public static final float[] L(float[] fArr) {
        float f2 = fArr[0];
        float f3 = fArr[3];
        float f4 = fArr[6];
        float f5 = fArr[1];
        float f6 = fArr[4];
        float f7 = fArr[7];
        float f8 = fArr[2];
        float f9 = fArr[5];
        float f10 = fArr[8];
        float f11 = (f6 * f10) - (f7 * f9);
        float f12 = (f7 * f8) - (f5 * f10);
        float f13 = (f5 * f9) - (f6 * f8);
        float f14 = (f4 * f13) + (f3 * f12) + (f2 * f11);
        float[] fArr2 = new float[fArr.length];
        fArr2[0] = f11 / f14;
        fArr2[1] = f12 / f14;
        fArr2[2] = f13 / f14;
        fArr2[3] = ((f4 * f9) - (f3 * f10)) / f14;
        fArr2[4] = ((f10 * f2) - (f4 * f8)) / f14;
        fArr2[5] = ((f8 * f3) - (f9 * f2)) / f14;
        fArr2[6] = ((f3 * f7) - (f4 * f6)) / f14;
        fArr2[7] = ((f4 * f5) - (f7 * f2)) / f14;
        fArr2[8] = ((f2 * f6) - (f3 * f5)) / f14;
        return fArr2;
    }

    public static final cd0 M(cd0 cd0Var, lv lvVar) {
        return cd0Var.b(new n40(lvVar));
    }

    public static final float[] N(float[] fArr, float[] fArr2) {
        float[] fArr3 = new float[9];
        if (fArr.length < 9 || fArr2.length < 9) {
            return fArr3;
        }
        float f2 = fArr[0] * fArr2[0];
        float f3 = fArr[3];
        float f4 = fArr2[1];
        float f5 = fArr[6];
        float f6 = fArr2[2];
        fArr3[0] = (f5 * f6) + (f3 * f4) + f2;
        float f7 = fArr[1];
        float f8 = fArr2[0];
        float f9 = fArr[4];
        float f10 = fArr[7];
        float f11 = f10 * f6;
        fArr3[1] = f11 + (f4 * f9) + (f7 * f8);
        float f12 = fArr[2] * f8;
        float f13 = fArr[5];
        float f14 = (fArr2[1] * f13) + f12;
        float f15 = fArr[8];
        fArr3[2] = (f6 * f15) + f14;
        float f16 = fArr[0];
        float f17 = fArr2[3] * f16;
        float f18 = fArr2[4];
        float f19 = (f3 * f18) + f17;
        float f20 = fArr2[5];
        fArr3[3] = (f5 * f20) + f19;
        float f21 = fArr[1];
        float f22 = fArr2[3];
        float f23 = f9 * f18;
        fArr3[4] = (f10 * f20) + f23 + (f21 * f22);
        float f24 = fArr[2];
        float f25 = f20 * f15;
        fArr3[5] = f25 + (f13 * fArr2[4]) + (f22 * f24);
        float f26 = f16 * fArr2[6];
        float f27 = fArr[3];
        float f28 = fArr2[7];
        float f29 = (f27 * f28) + f26;
        float f30 = fArr2[8];
        fArr3[6] = (f5 * f30) + f29;
        float f31 = fArr2[6];
        float f32 = f10 * f30;
        fArr3[7] = f32 + (fArr[4] * f28) + (f21 * f31);
        float f33 = f15 * f30;
        fArr3[8] = f33 + (fArr[5] * fArr2[7]) + (f24 * f31);
        return fArr3;
    }

    public static final float[] O(float[] fArr, float[] fArr2) {
        if (fArr.length < 9 || fArr2.length < 3) {
            return fArr2;
        }
        float f2 = fArr2[0];
        float f3 = fArr2[1];
        float f4 = fArr2[2];
        fArr2[0] = (fArr[6] * f4) + (fArr[3] * f3) + (fArr[0] * f2);
        fArr2[1] = (fArr[7] * f4) + (fArr[4] * f3) + (fArr[1] * f2);
        fArr2[2] = (fArr[8] * f4) + (fArr[5] * f3) + (fArr[2] * f2);
        return fArr2;
    }

    public static final boolean P(mx0 mx0Var, gv gvVar) {
        int i2;
        b0 b0Var;
        Object e2;
        ww0 j2;
        boolean l;
        do {
            synchronized (h) {
                my0 my0Var = mx0Var.e;
                my0Var.getClass();
                my0 my0Var2 = (my0) cx0.h(my0Var);
                i2 = my0Var2.d;
                b0Var = my0Var2.c;
            }
            b0Var.getClass();
            yl0 e3 = b0Var.e();
            e2 = gvVar.e(e3);
            b0 c2 = e3.c();
            if (o20.e(c2, b0Var)) {
                break;
            }
            my0 my0Var3 = mx0Var.e;
            my0Var3.getClass();
            synchronized (cx0.c) {
                j2 = cx0.j();
                l = l((my0) cx0.x(my0Var3, mx0Var, j2), i2, c2, true);
            }
            cx0.o(j2, mx0Var);
        } while (!l);
        return ((Boolean) e2).booleanValue();
    }

    public static void Q(PackageInfo packageInfo, File file) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(new File(file, "profileinstaller_profileWrittenFor_lastUpdateTime.dat")));
            try {
                dataOutputStream.writeLong(packageInfo.lastUpdateTime);
                dataOutputStream.close();
            } finally {
            }
        } catch (IOException unused) {
        }
    }

    public static final void T(float[] fArr, float[] fArr2) {
        float x = x(fArr2, 0, fArr, 0);
        float x2 = x(fArr2, 0, fArr, 1);
        float x3 = x(fArr2, 0, fArr, 2);
        float x4 = x(fArr2, 0, fArr, 3);
        float x5 = x(fArr2, 1, fArr, 0);
        float x6 = x(fArr2, 1, fArr, 1);
        float x7 = x(fArr2, 1, fArr, 2);
        float x8 = x(fArr2, 1, fArr, 3);
        float x9 = x(fArr2, 2, fArr, 0);
        float x10 = x(fArr2, 2, fArr, 1);
        float x11 = x(fArr2, 2, fArr, 2);
        float x12 = x(fArr2, 2, fArr, 3);
        float x13 = x(fArr2, 3, fArr, 0);
        float x14 = x(fArr2, 3, fArr, 1);
        float x15 = x(fArr2, 3, fArr, 2);
        float x16 = x(fArr2, 3, fArr, 3);
        fArr[0] = x;
        fArr[1] = x2;
        fArr[2] = x3;
        fArr[3] = x4;
        fArr[4] = x5;
        fArr[5] = x6;
        fArr[6] = x7;
        fArr[7] = x8;
        fArr[8] = x9;
        fArr[9] = x10;
        fArr[10] = x11;
        fArr[11] = x12;
        fArr[12] = x13;
        fArr[13] = x14;
        fArr[14] = x15;
        fArr[15] = x16;
    }

    public static final l9 W(m9 m9Var, kv kvVar, bw bwVar) {
        m9Var.getClass();
        kvVar.getClass();
        boolean f2 = bwVar.f(m9Var) | bwVar.f(kvVar);
        Object L = bwVar.L();
        if (f2 || L == ph.a) {
            L = new l9(m9Var, kvVar);
            bwVar.f0(L);
        }
        return (l9) L;
    }

    public static final nt0 X(bw bwVar) {
        int i2 = 0;
        Object[] objArr = new Object[0];
        boolean d2 = bwVar.d(0);
        Object L = bwVar.L();
        if (d2 || L == ph.a) {
            L = new ht0(i2);
            bwVar.f0(L);
        }
        return (nt0) y20.t(objArr, nt0.j, (vu) L, bwVar, 0);
    }

    public static final View Y(bd0 bd0Var) {
        if (!bd0Var.e.r) {
            q00.b("Cannot get View because the Modifier node is not currently attached.");
        }
        return (View) c50.a(k81.E(bd0Var));
    }

    public static final String Z(float f2) {
        if (Float.isNaN(f2)) {
            return "NaN";
        }
        if (Float.isInfinite(f2)) {
            if (f2 < 0.0f) {
                return "-Infinity";
            }
            return "Infinity";
        }
        int max = Math.max(1, 0);
        float pow = (float) Math.pow(10.0d, max);
        float f3 = f2 * pow;
        int i2 = (int) f3;
        if (f3 - i2 >= 0.5f) {
            i2++;
        }
        float f4 = i2 / pow;
        if (max > 0) {
            return String.valueOf(f4);
        }
        return String.valueOf((int) f4);
    }

    public static d7 a(float f2, int i2) {
        if ((i2 & 2) != 0) {
            f2 = 0.0f;
        }
        return new d7(dl.t, Float.valueOf(0.0f), new e7(f2), Long.MIN_VALUE, Long.MIN_VALUE, false);
    }

    public static cd0 a0(nt0 nt0Var) {
        je0 je0Var = nt0Var.d;
        return n20.l(zc0.a, uy.c).b(new ot0(null, null, je0Var, dj0.e, nt0Var, true, true)).b(new bu0(nt0Var));
    }

    public static final void b(bw bwVar, int i2) {
        boolean z;
        bwVar.W(-1976427982);
        boolean z2 = false;
        if (i2 != 0) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i2 & 1, z)) {
            f31.b(null, kg.e, bwVar, 48, 1);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new yu0(i2, 5, z2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x01c3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x020a  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x01ca A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x02c6  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0216  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0105 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x02da A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x016c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r7v22, types: [boolean] */
    /* JADX WARN: Type inference failed for: r7v23 */
    /* JADX WARN: Type inference failed for: r7v24 */
    /* JADX WARN: Type inference failed for: r7v26, types: [java.io.OutputStream, java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r7v27, types: [int] */
    /* JADX WARN: Type inference failed for: r7v28 */
    /* JADX WARN: Type inference failed for: r7v29 */
    /* JADX WARN: Type inference failed for: r7v30 */
    /* JADX WARN: Type inference failed for: r7v31 */
    /* JADX WARN: Type inference failed for: r7v33, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v37 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v51 */
    /* JADX WARN: Type inference failed for: r7v52 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void b0(android.content.Context r18, java.util.concurrent.Executor r19, defpackage.wn0 r20, boolean r21) {
        /*
            Method dump skipped, instructions count: 749
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.o4.b0(android.content.Context, java.util.concurrent.Executor, wn0, boolean):void");
    }

    public static final v4 c(String str) {
        Set singleton = Collections.singleton(str);
        singleton.getClass();
        return new v4(singleton);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void d(gv gvVar, bw bwVar, int i2) {
        int i3;
        boolean z;
        int i4;
        long j2;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        boolean z11;
        boolean z12;
        boolean z13;
        boolean z14;
        gvVar.getClass();
        bwVar.W(690163439);
        if (bwVar.h(gvVar)) {
            i3 = 4;
        } else {
            i3 = 2;
        }
        int i5 = i2 | i3;
        int i6 = 0;
        if ((i5 & 3) != 2) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i5 & 1, z)) {
            if (!n20.D(bwVar)) {
                j2 = se.b;
            } else {
                j2 = se.c;
            }
            long j3 = j2;
            cd0 b2 = jc0.K(jc0.K(a0(X(bwVar)), jc0.o), jc0.p).b(k81.n);
            x7 x7Var = new x7(16.0f, true, new v7(i6));
            z9 z9Var = x1.r;
            ef a2 = cf.a(x7Var, z9Var, bwVar, 6);
            long j4 = bwVar.T;
            int i7 = (int) (j4 ^ (j4 >>> 32));
            ll0 l = bwVar.l();
            cd0 B = dl.B(bwVar, b2);
            jh.c.getClass();
            di diVar = ih.b;
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            kf kfVar = ih.e;
            m20.F(kfVar, bwVar, a2);
            kf kfVar2 = ih.d;
            m20.F(kfVar2, bwVar, l);
            Integer valueOf = Integer.valueOf(i7);
            kf kfVar3 = ih.f;
            m20.F(kfVar3, bwVar, valueOf);
            w3 w3Var = ih.g;
            m20.C(w3Var, bwVar);
            kf kfVar4 = ih.c;
            m20.F(kfVar4, bwVar, B);
            zc0 zc0Var = zc0.a;
            int i8 = 0;
            dl.b("Backdrop Catalog", dl.E(zc0Var, 16.0f, 40.0f, 16.0f, 16.0f), new r11(j3, d20.A(4294967296L, 28.0f), nu.h, 16777208), 0, false, 0, 0, null, bwVar, 54, 1016);
            ef a3 = cf.a(o20.b, z9Var, bwVar, 0);
            long j5 = bwVar.T;
            int i9 = (int) (j5 ^ (j5 >>> 32));
            ll0 l2 = bwVar.l();
            cd0 B2 = dl.B(bwVar, zc0Var);
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            m20.F(kfVar, bwVar, a3);
            m20.F(kfVar2, bwVar, l2);
            d3.x(i9, bwVar, kfVar3, bwVar, w3Var);
            m20.F(kfVar4, bwVar, B2);
            int i10 = 6;
            g("Liquid glass components", bwVar, 6);
            int i11 = i5 & 14;
            if (i11 == 4) {
                z2 = true;
            } else {
                z2 = false;
            }
            Object L = bwVar.L();
            dt0 dt0Var = ph.a;
            if (z2 || L == dt0Var) {
                L = new qy(gvVar, i8);
                bwVar.f0(L);
            }
            e((vu) L, "Buttons", bwVar, 48);
            if (i11 == 4) {
                z3 = true;
            } else {
                z3 = false;
            }
            Object L2 = bwVar.L();
            if (!z3 && L2 != dt0Var) {
                i4 = 5;
            } else {
                i4 = 5;
                L2 = new qy(gvVar, i4);
                bwVar.f0(L2);
            }
            e((vu) L2, "Toggle", bwVar, 48);
            if (i11 == 4) {
                z4 = true;
            } else {
                z4 = false;
            }
            Object L3 = bwVar.L();
            if (z4 || L3 == dt0Var) {
                L3 = new qy(gvVar, i10);
                bwVar.f0(L3);
            }
            e((vu) L3, "Slider", bwVar, 48);
            if (i11 == 4) {
                z5 = true;
            } else {
                z5 = false;
            }
            Object L4 = bwVar.L();
            if (z5 || L4 == dt0Var) {
                L4 = new qy(gvVar, 7);
                bwVar.f0(L4);
            }
            e((vu) L4, "Bottom tabs", bwVar, 48);
            if (i11 == 4) {
                z6 = true;
            } else {
                z6 = false;
            }
            Object L5 = bwVar.L();
            if (z6 || L5 == dt0Var) {
                L5 = new qy(gvVar, 8);
                bwVar.f0(L5);
            }
            e((vu) L5, "Dialog", bwVar, 48);
            g("System UIs", bwVar, 6);
            if (i11 == 4) {
                z7 = true;
            } else {
                z7 = false;
            }
            Object L6 = bwVar.L();
            if (z7 || L6 == dt0Var) {
                L6 = new qy(gvVar, 9);
                bwVar.f0(L6);
            }
            e((vu) L6, "Lock screen (SDF texture)", bwVar, 48);
            if (i11 == 4) {
                z8 = true;
            } else {
                z8 = false;
            }
            Object L7 = bwVar.L();
            if (z8 || L7 == dt0Var) {
                L7 = new qy(gvVar, 10);
                bwVar.f0(L7);
            }
            e((vu) L7, "Control center", bwVar, 48);
            if (i11 == 4) {
                z9 = true;
            } else {
                z9 = false;
            }
            Object L8 = bwVar.L();
            if (z9 || L8 == dt0Var) {
                L8 = new qy(gvVar, 11);
                bwVar.f0(L8);
            }
            e((vu) L8, "Magnifier", bwVar, 48);
            g("Experiments", bwVar, 6);
            if (i11 == 4) {
                z10 = true;
            } else {
                z10 = false;
            }
            Object L9 = bwVar.L();
            if (z10 || L9 == dt0Var) {
                L9 = new qy(gvVar, 12);
                bwVar.f0(L9);
            }
            e((vu) L9, "Glass playground", bwVar, 48);
            if (i11 == 4) {
                z11 = true;
            } else {
                z11 = false;
            }
            Object L10 = bwVar.L();
            if (!z11 && L10 != dt0Var) {
                z12 = true;
            } else {
                z12 = true;
                L10 = new qy(gvVar, 1 == true ? 1 : 0);
                bwVar.f0(L10);
            }
            e((vu) L10, "Adaptive luminance glass", bwVar, 48);
            if (i11 == 4) {
                z13 = z12;
            } else {
                z13 = false;
            }
            Object L11 = bwVar.L();
            if (z13 || L11 == dt0Var) {
                L11 = new qy(gvVar, 2);
                bwVar.f0(L11);
            }
            e((vu) L11, "Progressive blur", bwVar, 48);
            if (i11 == 4) {
                z14 = z12;
            } else {
                z14 = false;
            }
            Object L12 = bwVar.L();
            if (z14 || L12 == dt0Var) {
                L12 = new qy(gvVar, 3);
                bwVar.f0(L12);
            }
            e((vu) L12, "Scroll container", bwVar, 48);
            int i12 = 4;
            if (i11 == 4) {
                i8 = z12;
            }
            Object L13 = bwVar.L();
            if (i8 != 0 || L13 == dt0Var) {
                L13 = new qy(gvVar, i12);
                bwVar.f0(L13);
            }
            e((vu) L13, "Lazy scroll container", bwVar, 48);
            bwVar.p(z12);
            bwVar.p(z12);
        } else {
            i4 = 5;
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new o(i2, i4, gvVar);
        }
    }

    public static final void e(vu vuVar, String str, bw bwVar, int i2) {
        int i3;
        boolean z;
        long j2;
        bwVar.W(243599390);
        if (bwVar.h(vuVar)) {
            i3 = 4;
        } else {
            i3 = 2;
        }
        int i4 = i3 | i2;
        if ((i4 & 19) != 18) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i4 & 1, z)) {
            if (!n20.D(bwVar)) {
                j2 = se.b;
            } else {
                j2 = se.c;
            }
            dl.b(str, k81.r(dl.C(n20.k(zc0.a, vuVar), 16.0f), 1.0f), new r11(j2, d20.A(4294967296L, 17.0f), null, 16777212), 0, false, 0, 0, null, bwVar, 6, 1016);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new v2(vuVar, str, i2);
        }
    }

    public static final r5 f() {
        return new r5(new Paint(7));
    }

    public static final void g(String str, bw bwVar, int i2) {
        boolean z;
        String str2;
        bw bwVar2;
        bwVar.W(1446627865);
        if ((i2 & 3) != 2) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i2 & 1, z)) {
            str2 = str;
            bwVar2 = bwVar;
            dl.b(str2, k81.r(dl.E(zc0.a, 16.0f, 24.0f, 16.0f, 8.0f), 1.0f), new r11(f31.f(4278225151L), d20.A(4294967296L, 15.0f), nu.h, 16777208), 0, false, 0, 0, null, bwVar2, 438, 1016);
        } else {
            str2 = str;
            bwVar2 = bwVar;
            bwVar2.R();
        }
        mo0 r = bwVar2.r();
        if (r != null) {
            r.d = new o(i2, 6, str2);
        }
    }

    public static final boolean h(ho hoVar, long j2) {
        if (hoVar.e.r) {
            w00 w00Var = k81.E(hoVar).H.c;
            if (w00Var.U.r) {
                long Y0 = w00Var.Y0(0L);
                float intBitsToFloat = Float.intBitsToFloat((int) (Y0 >> 32));
                float intBitsToFloat2 = Float.intBitsToFloat((int) (Y0 & 4294967295L));
                long j3 = hoVar.u;
                float f2 = ((int) (j3 >> 32)) + intBitsToFloat;
                float f3 = ((int) (j3 & 4294967295L)) + intBitsToFloat2;
                float intBitsToFloat3 = Float.intBitsToFloat((int) (j2 >> 32));
                if (intBitsToFloat <= intBitsToFloat3 && intBitsToFloat3 <= f2) {
                    float intBitsToFloat4 = Float.intBitsToFloat((int) (j2 & 4294967295L));
                    if (intBitsToFloat2 <= intBitsToFloat4 && intBitsToFloat4 <= f3) {
                        return true;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public static final void i(int i2, int i3) {
        if (i2 >= 0 && i2 < i3) {
            return;
        }
        throw new IndexOutOfBoundsException("index (" + i2 + ") is out of bound of [0, " + i3 + ')');
    }

    public static we j(we weVar) {
        c61 c61Var = dl.m;
        if (k81.q(weVar.b, 12884901888L)) {
            wq0 wq0Var = (wq0) weVar;
            c61 c61Var2 = wq0Var.d;
            if (!u(c61Var2, c61Var)) {
                return new wq0(wq0Var.a, wq0Var.h, c61Var, N(t((float[]) j2.g.f, c61Var2.a(), c61Var.a()), wq0Var.i), wq0Var.k, wq0Var.n, wq0Var.e, wq0Var.f, wq0Var.g, -1);
            }
        }
        return weVar;
    }

    public static final void k(k1 k1Var, su0 su0Var) {
        if (n20.f(su0Var)) {
            Object g2 = su0Var.d.e.g(mu0.i);
            if (g2 == null) {
                g2 = null;
            }
            n0 n0Var = (n0) g2;
            if (n0Var != null) {
                k1Var.a(new e1(null, android.R.id.accessibilityActionSetProgress, n0Var.a, null));
            }
        }
    }

    public static final boolean l(my0 my0Var, int i2, b0 b0Var, boolean z) {
        boolean z2;
        synchronized (h) {
            try {
                int i3 = my0Var.d;
                if (i3 == i2) {
                    my0Var.c = b0Var;
                    z2 = true;
                    if (z) {
                        my0Var.e++;
                    }
                    my0Var.d = i3 + 1;
                } else {
                    z2 = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z2;
    }

    public static final int m(int[] iArr, int i2, int i3) {
        iArr.getClass();
        int i4 = i2 - 1;
        int i5 = 0;
        while (i5 <= i4) {
            int i6 = (i5 + i4) >>> 1;
            int i7 = iArr[i6];
            if (i7 < i3) {
                i5 = i6 + 1;
            } else if (i7 > i3) {
                i4 = i6 - 1;
            } else {
                return i6;
            }
        }
        return ~i5;
    }

    public static final int n(long[] jArr, int i2, long j2) {
        jArr.getClass();
        int i3 = i2 - 1;
        int i4 = 0;
        while (i4 <= i3) {
            int i5 = (i4 + i3) >>> 1;
            long j3 = jArr[i5];
            if (j3 < j2) {
                i4 = i5 + 1;
            } else if (j3 > j2) {
                i3 = i5 - 1;
            } else {
                return i5;
            }
        }
        return ~i4;
    }

    public static void o(np npVar, float f2) {
        npVar.getClass();
        if (Build.VERSION.SDK_INT < 31 || f2 <= 0.0f) {
            return;
        }
        if (npVar.j != null && f2 > npVar.i) {
            npVar.i = f2;
        }
        npVar.j = new ia(npVar.j, f2, f2, 0);
    }

    public static final void s(long j2, dj0 dj0Var) {
        if (dj0Var == dj0.e) {
            if (si.g(j2) == Integer.MAX_VALUE) {
                t00.c("Vertically scrollable component was measured with an infinity maximum height constraints, which is disallowed. One of the common reasons is nesting layouts like LazyColumn and Column(Modifier.verticalScroll()). If you want to add a header before the list of items please add a header as a separate item() before the main items() inside the LazyColumn scope. There could be other reasons for this to happen: your ComposeView was added into a LinearLayout with some weight, you applied Modifier.wrapContentSize(unbounded = true) or wrote a custom layout. Please try to remove the source of infinite constraints in the hierarchy above the scrolling container.");
            }
        } else {
            if (si.h(j2) != Integer.MAX_VALUE) {
                return;
            }
            t00.c("Horizontally scrollable component was measured with an infinity maximum width constraints, which is disallowed. One of the common reasons is nesting layouts like LazyRow and Row(Modifier.horizontalScroll()). If you want to add a header before the list of items please add a header as a separate item() before the main items() inside the LazyRow scope. There could be other reasons for this to happen: your ComposeView was added into a LinearLayout with some weight, you applied Modifier.wrapContentSize(unbounded = true) or wrote a custom layout. Please try to remove the source of infinite constraints in the hierarchy above the scrolling container.");
        }
    }

    public static final float[] t(float[] fArr, float[] fArr2, float[] fArr3) {
        O(fArr, fArr2);
        O(fArr, fArr3);
        float[] fArr4 = {fArr3[0] / fArr2[0], fArr3[1] / fArr2[1], fArr3[2] / fArr2[2]};
        float[] L = L(fArr);
        float f2 = fArr4[0];
        float f3 = fArr[0] * f2;
        float f4 = fArr4[1];
        float f5 = fArr[1] * f4;
        float f6 = fArr4[2];
        return N(L, new float[]{f3, f5, fArr[2] * f6, fArr[3] * f2, fArr[4] * f4, fArr[5] * f6, f2 * fArr[6], f4 * fArr[7], f6 * fArr[8]});
    }

    public static final boolean u(c61 c61Var, c61 c61Var2) {
        if (c61Var == c61Var2) {
            return true;
        }
        if (Math.abs(c61Var.a - c61Var2.a) < 0.001f && Math.abs(c61Var.b - c61Var2.b) < 0.001f) {
            return true;
        }
        return false;
    }

    public static d7 v(d7 d7Var, float f2) {
        float f3 = ((e7) d7Var.g).a;
        return new d7(d7Var.e, Float.valueOf(f2), new e7(f3), d7Var.h, d7Var.i, d7Var.j);
    }

    public static final pi w(we weVar, we weVar2) {
        if (weVar == weVar2) {
            return new pi(weVar, weVar, 1);
        }
        if (k81.q(weVar.b, 12884901888L) && k81.q(weVar2.b, 12884901888L)) {
            return new oi((wq0) weVar, (wq0) weVar2);
        }
        return new pi(weVar, weVar2, 0);
    }

    public static final float x(float[] fArr, int i2, float[] fArr2, int i3) {
        int i4 = i2 * 4;
        return (fArr[i4 + 3] * fArr2[12 + i3]) + (fArr[i4 + 2] * fArr2[8 + i3]) + (fArr[i4 + 1] * fArr2[4 + i3]) + (fArr[i4] * fArr2[i3]);
    }

    public static final cd0 y(cd0 cd0Var, gv gvVar) {
        return cd0Var.b(new qp(gvVar));
    }

    public static final cd0 z(cd0 cd0Var, gv gvVar) {
        return cd0Var.b(new wp(gvVar));
    }

    public abstract void R(Throwable th);

    public abstract void S(e3 e3Var);

    public abstract void U(i0 i0Var, i0 i0Var2);

    public abstract void V(i0 i0Var, Thread thread);

    public abstract boolean p(j0 j0Var, f0 f0Var);

    public abstract boolean q(j0 j0Var, Object obj, Object obj2);

    public abstract boolean r(j0 j0Var, i0 i0Var, i0 i0Var2);
}
