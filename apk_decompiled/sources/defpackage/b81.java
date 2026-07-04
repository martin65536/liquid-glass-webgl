package defpackage;

import android.view.View;
import com.kyant.backdrop.catalog.R;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class b81 {
    public static final ve0 a;

    static {
        long[] jArr = zs0.a;
        a = new ve0();
    }

    public static final th a(View view) {
        Object tag = view.getTag(R.id.androidx_compose_ui_view_composition_context);
        if (tag instanceof th) {
            return (th) tag;
        }
        return null;
    }
}
