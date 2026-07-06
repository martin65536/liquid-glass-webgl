package defpackage;

import android.view.View;
import android.view.translation.ViewTranslationCallback;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class k4 implements ViewTranslationCallback {
    public static final k4 a = new Object();

    public final boolean onClearTranslation(View view) {
        vu vuVar;
        view.getClass();
        t4 contentCaptureManager$ui = ((b4) view).getContentCaptureManager$ui();
        contentCaptureManager$ui.getClass();
        contentCaptureManager$ui.j = q4.e;
        t10 j = contentCaptureManager$ui.j();
        Object[] objArr = j.c;
        long[] jArr = j.a;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j2 = jArr[i];
                if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j2) < 128) {
                            ve0 ve0Var = ((uu0) objArr[(i << 3) + i3]).a.d.e;
                            Object g = ve0Var.g(wu0.B);
                            Object obj = null;
                            if (g == null) {
                                g = null;
                            }
                            if (g != null) {
                                Object g2 = ve0Var.g(mu0.n);
                                if (g2 != null) {
                                    obj = g2;
                                }
                                n0 n0Var = (n0) obj;
                                if (n0Var != null && (vuVar = (vu) n0Var.b) != null) {
                                }
                            }
                        }
                        j2 >>= 8;
                    }
                    if (i2 != 8) {
                        return true;
                    }
                }
                if (i != length) {
                    i++;
                } else {
                    return true;
                }
            }
        } else {
            return true;
        }
    }

    public final boolean onHideTranslation(View view) {
        gv gvVar;
        view.getClass();
        t4 contentCaptureManager$ui = ((b4) view).getContentCaptureManager$ui();
        contentCaptureManager$ui.getClass();
        contentCaptureManager$ui.j = q4.e;
        t10 j = contentCaptureManager$ui.j();
        Object[] objArr = j.c;
        long[] jArr = j.a;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j2 = jArr[i];
                if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j2) < 128) {
                            ve0 ve0Var = ((uu0) objArr[(i << 3) + i3]).a.d.e;
                            Object g = ve0Var.g(wu0.B);
                            Object obj = null;
                            if (g == null) {
                                g = null;
                            }
                            if (o20.e(g, Boolean.TRUE)) {
                                Object g2 = ve0Var.g(mu0.m);
                                if (g2 != null) {
                                    obj = g2;
                                }
                                n0 n0Var = (n0) obj;
                                if (n0Var != null && (gvVar = (gv) n0Var.b) != null) {
                                }
                            }
                        }
                        j2 >>= 8;
                    }
                    if (i2 != 8) {
                        return true;
                    }
                }
                if (i != length) {
                    i++;
                } else {
                    return true;
                }
            }
        } else {
            return true;
        }
    }

    public final boolean onShowTranslation(View view) {
        gv gvVar;
        view.getClass();
        t4 contentCaptureManager$ui = ((b4) view).getContentCaptureManager$ui();
        contentCaptureManager$ui.getClass();
        contentCaptureManager$ui.j = q4.f;
        t10 j = contentCaptureManager$ui.j();
        Object[] objArr = j.c;
        long[] jArr = j.a;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j2 = jArr[i];
                if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j2) < 128) {
                            ve0 ve0Var = ((uu0) objArr[(i << 3) + i3]).a.d.e;
                            Object g = ve0Var.g(wu0.B);
                            Object obj = null;
                            if (g == null) {
                                g = null;
                            }
                            if (o20.e(g, Boolean.FALSE)) {
                                Object g2 = ve0Var.g(mu0.m);
                                if (g2 != null) {
                                    obj = g2;
                                }
                                n0 n0Var = (n0) obj;
                                if (n0Var != null && (gvVar = (gv) n0Var.b) != null) {
                                }
                            }
                        }
                        j2 >>= 8;
                    }
                    if (i2 != 8) {
                        return true;
                    }
                }
                if (i != length) {
                    i++;
                } else {
                    return true;
                }
            }
        } else {
            return true;
        }
    }
}
