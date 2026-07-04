package defpackage;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class a90 extends qv0 {
    public final ArrayList u;
    public final ArrayList v;
    public final long w;
    public final long x;
    public final int y;

    public a90(ArrayList arrayList, ArrayList arrayList2, long j, long j2, int i) {
        this.u = arrayList;
        this.v = arrayList2;
        this.w = j;
        this.x = j2;
        this.y = i;
    }

    @Override // defpackage.qv0
    public final Shader N(long j) {
        long j2 = this.w;
        int i = (int) (j2 >> 32);
        if (Float.intBitsToFloat(i) == Float.POSITIVE_INFINITY) {
            i = (int) (j >> 32);
        }
        float intBitsToFloat = Float.intBitsToFloat(i);
        int i2 = (int) (j2 & 4294967295L);
        if (Float.intBitsToFloat(i2) == Float.POSITIVE_INFINITY) {
            i2 = (int) (j & 4294967295L);
        }
        float intBitsToFloat2 = Float.intBitsToFloat(i2);
        long j3 = this.x;
        int i3 = (int) (j3 >> 32);
        if (Float.intBitsToFloat(i3) == Float.POSITIVE_INFINITY) {
            i3 = (int) (j >> 32);
        }
        float intBitsToFloat3 = Float.intBitsToFloat(i3);
        int i4 = (int) (j3 & 4294967295L);
        if (Float.intBitsToFloat(i4) == Float.POSITIVE_INFINITY) {
            i4 = (int) (j & 4294967295L);
        }
        float intBitsToFloat4 = Float.intBitsToFloat(i4);
        long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat) << 32) | (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L);
        long floatToRawIntBits2 = (Float.floatToRawIntBits(intBitsToFloat3) << 32) | (Float.floatToRawIntBits(intBitsToFloat4) & 4294967295L);
        ArrayList arrayList = this.u;
        ArrayList arrayList2 = this.v;
        n20.O(arrayList, arrayList2);
        int o = n20.o(arrayList);
        return new LinearGradient(Float.intBitsToFloat((int) (floatToRawIntBits >> 32)), Float.intBitsToFloat((int) (floatToRawIntBits & 4294967295L)), Float.intBitsToFloat((int) (floatToRawIntBits2 >> 32)), Float.intBitsToFloat((int) (floatToRawIntBits2 & 4294967295L)), n20.E(o, arrayList), n20.F(arrayList2, arrayList, o), jc0.I(this.y));
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof a90) {
                a90 a90Var = (a90) obj;
                if (this.u.equals(a90Var.u) && this.v.equals(a90Var.v) && ch0.c(this.w, a90Var.w) && ch0.c(this.x, a90Var.x) && this.y == a90Var.y) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return ((ch0.e(this.x) + ((ch0.e(this.w) + ((this.v.hashCode() + (this.u.hashCode() * 31)) * 31)) * 31)) * 31) + this.y;
    }

    public final String toString() {
        String str;
        long j = this.w;
        String str2 = "";
        if (((((j & 9187343241974906880L) ^ 9187343241974906880L) - 4294967297L) & (-9223372034707292160L)) != 0) {
            str = "";
        } else {
            str = "start=" + ((Object) ch0.i(j)) + ", ";
        }
        long j2 = this.x;
        if (((((j2 & 9187343241974906880L) ^ 9187343241974906880L) - 4294967297L) & (-9223372034707292160L)) == 0) {
            str2 = "end=" + ((Object) ch0.i(j2)) + ", ";
        }
        return "LinearGradient(colors=" + this.u + ", stops=" + this.v + ", " + str + str2 + "tileMode=" + ((Object) t20.S(this.y)) + ')';
    }
}
