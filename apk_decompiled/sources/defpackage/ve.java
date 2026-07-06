package defpackage;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Build;
import java.util.Arrays;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ve extends te {
    public float[] b;

    public final float[] a() {
        float[] fArr = this.b;
        if (fArr == null) {
            ColorFilter colorFilter = this.a;
            if ((colorFilter instanceof ColorMatrixColorFilter) && 26 <= Build.VERSION.SDK_INT) {
                ColorMatrix colorMatrix = new ColorMatrix();
                ((ColorMatrixColorFilter) colorFilter).getColorMatrix(colorMatrix);
                float[] array = colorMatrix.getArray();
                this.b = array;
                return array;
            }
            v7.m("Unable to obtain ColorMatrix from Android ColorMatrixColorFilter. This method was invoked on an unsupported Android version");
            return null;
        }
        return fArr;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof ve) && Arrays.equals(a(), ((ve) obj).a())) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        float[] fArr = this.b;
        if (fArr != null) {
            return Arrays.hashCode(fArr);
        }
        return 0;
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("ColorMatrixColorFilter(colorMatrix=");
        float[] fArr = this.b;
        if (fArr == null) {
            str = "null";
        } else {
            str = "ColorMatrix(values=" + Arrays.toString(fArr) + ')';
        }
        sb.append((Object) str);
        sb.append(')');
        return sb.toString();
    }
}
