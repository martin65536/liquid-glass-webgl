package defpackage;

import android.content.res.Configuration;
import android.os.Build;
import android.os.SystemClock;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.Locale;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class t3 extends z30 implements vu {
    public final /* synthetic */ int f;
    public final /* synthetic */ b4 g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ t3(b4 b4Var, int i) {
        super(0);
        this.f = i;
        this.g = b4Var;
    }

    @Override // defpackage.vu
    public final Object a() {
        va0 a;
        int actionMasked;
        int i = this.f;
        b4 b4Var = this.g;
        switch (i) {
            case 0:
                Boolean bool = (Boolean) b4Var.u.getValue();
                bool.getClass();
                return bool;
            case 1:
                Configuration configuration = b4Var.getConfiguration();
                int i2 = Build.VERSION.SDK_INT;
                if (i2 >= 24) {
                    a = new va0(new ya0(li.c(configuration)));
                } else {
                    a = va0.a(configuration.locale);
                }
                if (a.a.isEmpty()) {
                    if (i2 >= 24) {
                        a = new va0(new ya0(li.b()));
                    } else {
                        a = va0.a(Locale.getDefault());
                    }
                }
                xa0 xa0Var = a.a;
                int size = xa0Var.size();
                ArrayList arrayList = new ArrayList(size);
                for (int i3 = 0; i3 < size; i3++) {
                    Locale locale = xa0Var.get(i3);
                    locale.getClass();
                    arrayList.add(new ta0(locale));
                }
                return new ua0(arrayList);
            case 2:
                MotionEvent motionEvent = b4Var.y0;
                if (motionEvent != null && ((actionMasked = motionEvent.getActionMasked()) == 7 || actionMasked == 9)) {
                    b4Var.z0 = SystemClock.uptimeMillis();
                    b4Var.post(b4Var.E0);
                }
                return x31.a;
            default:
                b4Var.get_viewTreeOwners();
                return null;
        }
    }
}
