package defpackage;

import android.graphics.ColorSpace;
import android.os.Build;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.autofill.AutofillId;
import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class ye {
    public static final ColorSpace a(we weVar) {
        ColorSpace colorSpace;
        ColorSpace.Named named;
        ColorSpace.Named named2;
        if (o20.e(weVar, af.e)) {
            return ColorSpace.get(ColorSpace.Named.SRGB);
        }
        if (o20.e(weVar, af.q)) {
            return ColorSpace.get(ColorSpace.Named.ACES);
        }
        if (o20.e(weVar, af.r)) {
            return ColorSpace.get(ColorSpace.Named.ACESCG);
        }
        if (o20.e(weVar, af.o)) {
            return ColorSpace.get(ColorSpace.Named.ADOBE_RGB);
        }
        if (o20.e(weVar, af.j)) {
            return ColorSpace.get(ColorSpace.Named.BT2020);
        }
        if (o20.e(weVar, af.i)) {
            return ColorSpace.get(ColorSpace.Named.BT709);
        }
        if (o20.e(weVar, af.t)) {
            return ColorSpace.get(ColorSpace.Named.CIE_LAB);
        }
        if (o20.e(weVar, af.s)) {
            return ColorSpace.get(ColorSpace.Named.CIE_XYZ);
        }
        if (o20.e(weVar, af.k)) {
            return ColorSpace.get(ColorSpace.Named.DCI_P3);
        }
        if (o20.e(weVar, af.l)) {
            return ColorSpace.get(ColorSpace.Named.DISPLAY_P3);
        }
        if (o20.e(weVar, af.g)) {
            return ColorSpace.get(ColorSpace.Named.EXTENDED_SRGB);
        }
        if (o20.e(weVar, af.h)) {
            return ColorSpace.get(ColorSpace.Named.LINEAR_EXTENDED_SRGB);
        }
        if (o20.e(weVar, af.f)) {
            return ColorSpace.get(ColorSpace.Named.LINEAR_SRGB);
        }
        if (o20.e(weVar, af.m)) {
            return ColorSpace.get(ColorSpace.Named.NTSC_1953);
        }
        if (o20.e(weVar, af.p)) {
            return ColorSpace.get(ColorSpace.Named.PRO_PHOTO_RGB);
        }
        if (o20.e(weVar, af.n)) {
            return ColorSpace.get(ColorSpace.Named.SMPTE_C);
        }
        ColorSpace.Rgb.TransferParameters transferParameters = null;
        if (Build.VERSION.SDK_INT >= 34) {
            if (o20.e(weVar, af.v)) {
                named2 = ColorSpace.Named.BT2020_HLG;
                colorSpace = ColorSpace.get(named2);
            } else if (o20.e(weVar, af.w)) {
                named = ColorSpace.Named.BT2020_PQ;
                colorSpace = ColorSpace.get(named);
            } else {
                colorSpace = null;
            }
            if (colorSpace != null) {
                return colorSpace;
            }
        }
        if (weVar instanceof wq0) {
            String str = weVar.a;
            wq0 wq0Var = (wq0) weVar;
            float[] a = wq0Var.d.a();
            q21 q21Var = wq0Var.g;
            if (q21Var != null) {
                transferParameters = new ColorSpace.Rgb.TransferParameters(q21Var.b, q21Var.c, q21Var.d, q21Var.e, q21Var.f, q21Var.g, q21Var.a);
            }
            float[] fArr = wq0Var.i;
            final int i = 0;
            if (transferParameters != null) {
                ColorSpace.Rgb rgb = new ColorSpace.Rgb(str, wq0Var.h, a, transferParameters);
                if (Float.isNaN(fArr[0]) || Arrays.equals(rgb.getTransform(), fArr)) {
                    return rgb;
                }
                return new ColorSpace.Rgb(str, fArr, transferParameters);
            }
            float[] fArr2 = wq0Var.h;
            final vq0 vq0Var = wq0Var.l;
            DoubleUnaryOperator doubleUnaryOperator = new DoubleUnaryOperator() { // from class: xe
                @Override // java.util.function.DoubleUnaryOperator
                public final double applyAsDouble(double d) {
                    int i2 = i;
                    gv gvVar = vq0Var;
                    switch (i2) {
                        case 0:
                            return ((Number) gvVar.e(Double.valueOf(d))).doubleValue();
                        default:
                            return ((Number) gvVar.e(Double.valueOf(d))).doubleValue();
                    }
                }
            };
            final vq0 vq0Var2 = wq0Var.o;
            final int i2 = 1;
            return new ColorSpace.Rgb(str, fArr2, a, doubleUnaryOperator, new DoubleUnaryOperator() { // from class: xe
                @Override // java.util.function.DoubleUnaryOperator
                public final double applyAsDouble(double d) {
                    int i22 = i2;
                    gv gvVar = vq0Var2;
                    switch (i22) {
                        case 0:
                            return ((Number) gvVar.e(Double.valueOf(d))).doubleValue();
                        default:
                            return ((Number) gvVar.e(Double.valueOf(d))).doubleValue();
                    }
                }
            }, wq0Var.e, wq0Var.f);
        }
        return ColorSpace.get(ColorSpace.Named.SRGB);
    }

    public static AutofillId b(View view) {
        return view.getAutofillId();
    }

    public static float c(ViewConfiguration viewConfiguration) {
        return viewConfiguration.getScaledHorizontalScrollFactor();
    }

    public static float d(ViewConfiguration viewConfiguration) {
        return viewConfiguration.getScaledHorizontalScrollFactor();
    }

    public static float e(ViewConfiguration viewConfiguration) {
        return viewConfiguration.getScaledVerticalScrollFactor();
    }

    public static float f(ViewConfiguration viewConfiguration) {
        return viewConfiguration.getScaledVerticalScrollFactor();
    }
}
