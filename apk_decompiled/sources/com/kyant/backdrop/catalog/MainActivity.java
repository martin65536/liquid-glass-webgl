package com.kyant.backdrop.catalog;

import android.R;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import defpackage.cg;
import defpackage.dg;
import defpackage.f1;
import defpackage.g30;
import defpackage.gg;
import defpackage.jc0;
import defpackage.kh;
import defpackage.n30;
import defpackage.o30;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class MainActivity extends cg {
    @Override // defpackage.cg, defpackage.bg, android.app.Activity
    public final void onCreate(Bundle bundle) {
        kh khVar;
        super.onCreate(bundle);
        Window window = getWindow();
        int i = Build.VERSION.SDK_INT;
        if (i >= 35) {
            f1.e(window);
        } else if (i >= 30) {
            f1.d(window);
        } else {
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 1792);
        }
        gg ggVar = jc0.a;
        ViewGroup.LayoutParams layoutParams = dg.a;
        View childAt = ((ViewGroup) getWindow().getDecorView().findViewById(R.id.content)).getChildAt(0);
        if (childAt instanceof kh) {
            khVar = (kh) childAt;
        } else {
            khVar = null;
        }
        if (khVar != null) {
            khVar.setParentCompositionContext(null);
            khVar.setContent(ggVar);
            return;
        }
        kh khVar2 = new kh(this);
        khVar2.setParentCompositionContext(null);
        khVar2.setContent(ggVar);
        View decorView2 = getWindow().getDecorView();
        if (g30.q(decorView2) == null) {
            decorView2.setTag(R.id.view_tree_lifecycle_owner, this);
        }
        if (o30.o(decorView2) == null) {
            decorView2.setTag(R.id.view_tree_view_model_store_owner, this);
        }
        if (n30.t(decorView2) == null) {
            decorView2.setTag(R.id.view_tree_saved_state_registry_owner, this);
        }
        setContentView(khVar2, dg.a);
    }
}
