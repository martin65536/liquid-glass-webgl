package defpackage;

import android.graphics.Shader;
import android.graphics.SweepGradient;
import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class zz0 extends qv0 {
    public final long u;
    public final ArrayList v;
    public final ArrayList w;

    public zz0(long j, ArrayList arrayList, ArrayList arrayList2) {
        this.u = j;
        this.v = arrayList;
        this.w = arrayList2;
    }

    @Override // defpackage.qv0
    public final Shader N(long j) {
        float intBitsToFloat;
        long floatToRawIntBits;
        long j2 = this.u;
        if ((9223372034707292159L & j2) == 9205357640488583168L) {
            floatToRawIntBits = o30.p(j);
        } else {
            int i = (int) (j2 >> 32);
            if (Float.intBitsToFloat(i) == Float.POSITIVE_INFINITY) {
                i = (int) (j >> 32);
            }
            float intBitsToFloat2 = Float.intBitsToFloat(i);
            int i2 = (int) (j2 & 4294967295L);
            if (Float.intBitsToFloat(i2) == Float.POSITIVE_INFINITY) {
                intBitsToFloat = Float.intBitsToFloat((int) (j & 4294967295L));
            } else {
                intBitsToFloat = Float.intBitsToFloat(i2);
            }
            floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat2) << 32);
        }
        ArrayList arrayList = this.v;
        ArrayList arrayList2 = this.w;
        n20.O(arrayList, arrayList2);
        int o = n20.o(arrayList);
        return new SweepGradient(Float.intBitsToFloat((int) (floatToRawIntBits >> 32)), Float.intBitsToFloat((int) (floatToRawIntBits & 4294967295L)), n20.E(o, arrayList), n20.F(arrayList2, arrayList, o));
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof zz0) {
                zz0 zz0Var = (zz0) obj;
                if (!ch0.c(this.u, zz0Var.u) || !this.v.equals(zz0Var.v) || !this.w.equals(zz0Var.w)) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return this.w.hashCode() + ((this.v.hashCode() + (ch0.e(this.u) * 31)) * 31);
    }

    public final String toString() {
        String str;
        long j = this.u;
        if ((9223372034707292159L & j) != 9205357640488583168L) {
            str = "center=" + ((Object) ch0.i(j)) + ", ";
        } else {
            str = "";
        }
        return "SweepGradient(" + str + "colors=" + this.v + ", stops=" + this.w + ')';
    }
}
