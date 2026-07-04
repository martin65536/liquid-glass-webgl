package defpackage;

import android.graphics.Rect;
import android.view.View;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class et {
    public static final int[] a = new int[2];
    public static final Rect b = new Rect();

    public static final wo0 a(View view, b4 b4Var) {
        int[] iArr = a;
        view.getLocationInWindow(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        b4Var.getLocationInWindow(iArr);
        int i3 = iArr[0];
        float f = i2 - iArr[1];
        view.getFocusedRect(b);
        float f2 = (i - i3) + r1.left;
        return new wo0(f2, r1.top + f, r1.width() + f2, f + r1.top + r1.height());
    }

    public static final bt b(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i != 17) {
                    if (i != 33) {
                        if (i != 66) {
                            if (i != 130) {
                                return null;
                            }
                            return new bt(6);
                        }
                        return new bt(4);
                    }
                    return new bt(5);
                }
                return new bt(3);
            }
            return new bt(1);
        }
        return new bt(2);
    }
}
