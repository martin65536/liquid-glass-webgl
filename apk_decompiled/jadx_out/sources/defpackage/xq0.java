package defpackage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.kyant.backdrop.catalog.R;
import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class xq0 extends ViewGroup {
    public final int e;
    public final ArrayList f;
    public final ArrayList g;
    public final c4 h;
    public int i;

    public xq0(Context context) {
        super(context);
        this.e = 5;
        ArrayList arrayList = new ArrayList();
        this.f = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.g = arrayList2;
        this.h = new c4(18);
        setClipChildren(false);
        View view = new View(context);
        addView(view);
        arrayList.add(view);
        arrayList2.add(view);
        this.i = 1;
        setTag(R.id.hide_in_inspector_tag, Boolean.TRUE);
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        setMeasuredDimension(0, 0);
    }

    @Override // android.view.View, android.view.ViewParent
    public final void requestLayout() {
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }
}
