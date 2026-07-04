package defpackage;

import java.util.Arrays;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ku implements iu {
    public final float[] a;
    public final float[] b;

    public ku(float[] fArr, float[] fArr2) {
        if (fArr.length == fArr2.length && fArr.length != 0) {
            this.a = fArr;
            this.b = fArr2;
        } else {
            v7.m("Array lengths must match and be nonzero");
            throw null;
        }
    }

    @Override // defpackage.iu
    public final float a(float f) {
        return rt.f(f, this.b, this.a);
    }

    @Override // defpackage.iu
    public final float b(float f) {
        return rt.f(f, this.a, this.b);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj != null && (obj instanceof ku)) {
                ku kuVar = (ku) obj;
                if (Arrays.equals(this.a, kuVar.a) && Arrays.equals(this.b, kuVar.b)) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return Arrays.hashCode(this.b) + (Arrays.hashCode(this.a) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("FontScaleConverter{fromSpValues=");
        String arrays = Arrays.toString(this.a);
        arrays.getClass();
        sb.append(arrays);
        sb.append(", toDpValues=");
        String arrays2 = Arrays.toString(this.b);
        arrays2.getClass();
        sb.append(arrays2);
        sb.append('}');
        return sb.toString();
    }
}
