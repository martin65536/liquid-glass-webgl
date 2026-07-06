package defpackage;

import android.graphics.Rect;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qa {
    public final int a;
    public final int b;
    public final int c;
    public final int d;

    static {
        new qa(0, 0, 0, 0);
    }

    public qa(int i, int i2, int i3, int i4) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
        if (i <= i3) {
            if (i2 <= i4) {
                return;
            } else {
                throw new IllegalArgumentException(d3.u("top must be less than or equal to bottom, top: ", i2, ", bottom: ", i4).toString());
            }
        }
        throw new IllegalArgumentException(d3.u("Left must be less than or equal to right, left: ", i, ", right: ", i3).toString());
    }

    public final boolean equals(Object obj) {
        Class<?> cls;
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            cls = obj.getClass();
        } else {
            cls = null;
        }
        if (!qa.class.equals(cls)) {
            return false;
        }
        obj.getClass();
        qa qaVar = (qa) obj;
        if (this.a == qaVar.a && this.b == qaVar.b && this.c == qaVar.c && this.d == qaVar.d) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return (((((this.a * 31) + this.b) * 31) + this.c) * 31) + this.d;
    }

    public final String toString() {
        return qa.class.getSimpleName() + " { [" + this.a + ',' + this.b + ',' + this.c + ',' + this.d + "] }";
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public qa(Rect rect) {
        this(rect.left, rect.top, rect.right, rect.bottom);
        rect.getClass();
    }
}
