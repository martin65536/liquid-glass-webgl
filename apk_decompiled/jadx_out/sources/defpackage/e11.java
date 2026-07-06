package defpackage;

import android.graphics.Matrix;
import android.view.Choreographer;
import android.view.View;
import android.view.inputmethod.CursorAnchorInfo;
import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class e11 {
    public final View a;

    public e11(View view, b4 b4Var) {
        int i;
        new j2(view);
        Choreographer.getInstance();
        this.a = view;
        long j = m11.b;
        int length = new l7("").f.length();
        int i2 = m11.c;
        int i3 = (int) (j >> 32);
        if (i3 < 0) {
            i = 0;
        } else {
            i = i3;
        }
        i = i > length ? length : i;
        int i4 = (int) (j & 4294967295L);
        int i5 = i4 >= 0 ? i4 : 0;
        length = i5 <= length ? i5 : length;
        if (i != i3 || length != i4) {
            n30.d(i, length);
        }
        int i6 = kz.e;
        new ArrayList();
        n30.z(new n9(16, this));
        new CursorAnchorInfo.Builder();
        new Matrix();
    }
}
