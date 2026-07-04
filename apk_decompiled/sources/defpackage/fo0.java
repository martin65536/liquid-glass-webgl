package defpackage;

import android.graphics.RadialGradient;
import android.graphics.Shader;
import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class fo0 extends qv0 {
    public final ArrayList u;
    public final ArrayList v;
    public final long w;
    public final float x;
    public final int y;

    public fo0(ArrayList arrayList, ArrayList arrayList2, long j, float f, int i) {
        this.u = arrayList;
        this.v = arrayList2;
        this.w = j;
        this.x = f;
        this.y = i;
    }

    @Override // defpackage.qv0
    public final Shader N(long j) {
        float intBitsToFloat;
        float intBitsToFloat2;
        long j2 = this.w;
        if ((9223372034707292159L & j2) == 9205357640488583168L) {
            long p = o30.p(j);
            intBitsToFloat = Float.intBitsToFloat((int) (p >> 32));
            intBitsToFloat2 = Float.intBitsToFloat((int) (p & 4294967295L));
        } else {
            int i = (int) (j2 >> 32);
            if (Float.intBitsToFloat(i) == Float.POSITIVE_INFINITY) {
                i = (int) (j >> 32);
            }
            intBitsToFloat = Float.intBitsToFloat(i);
            int i2 = (int) (j2 & 4294967295L);
            if (Float.intBitsToFloat(i2) == Float.POSITIVE_INFINITY) {
                i2 = (int) (j & 4294967295L);
            }
            intBitsToFloat2 = Float.intBitsToFloat(i2);
        }
        long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32);
        float f = this.x;
        if (f == Float.POSITIVE_INFINITY) {
            f = mw0.b(j) / 2.0f;
        }
        float f2 = f;
        ArrayList arrayList = this.u;
        ArrayList arrayList2 = this.v;
        n20.O(arrayList, arrayList2);
        int o = n20.o(arrayList);
        return new RadialGradient(Float.intBitsToFloat((int) (floatToRawIntBits >> 32)), Float.intBitsToFloat((int) (floatToRawIntBits & 4294967295L)), f2, n20.E(o, arrayList), n20.F(arrayList2, arrayList, o), jc0.I(this.y));
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof fo0) {
                fo0 fo0Var = (fo0) obj;
                if (this.u.equals(fo0Var.u) && this.v.equals(fo0Var.v) && ch0.c(this.w, fo0Var.w) && this.x == fo0Var.x && this.y == fo0Var.y) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return d3.s(this.x, (ch0.e(this.w) + ((this.v.hashCode() + (this.u.hashCode() * 31)) * 31)) * 31, 31) + this.y;
    }

    public final String toString() {
        String str;
        long j = this.w;
        String str2 = "";
        if ((9223372034707292159L & j) == 9205357640488583168L) {
            str = "";
        } else {
            str = "center=" + ((Object) ch0.i(j)) + ", ";
        }
        float f = this.x;
        if ((Float.floatToRawIntBits(f) & Integer.MAX_VALUE) < 2139095040) {
            str2 = "radius=" + f + ", ";
        }
        return "RadialGradient(colors=" + this.u + ", stops=" + this.v + ", " + str + str2 + "tileMode=" + ((Object) t20.S(this.y)) + ')';
    }
}
