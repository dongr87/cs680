package com.example.collegeguide;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.View;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;

public class CollegeGuide extends Activity {
	
	private TabHost tabs = null;
	private WebView web;
	private GoogleMap myMap = null;
	private Spinner spinner;
	private ArrayAdapter adapter;
	private String website = null;
	private static final float zoom = 11.0f;
	private static final LatLng Harvard = new LatLng(42.376892, -71.116660);
	private static final LatLng MIT = new LatLng(42.360107, -71.094310);
	private static final LatLng BU = new LatLng(42.350532, -71.105538);
	private static final LatLng Bentley = new LatLng(42.385810, -71.221852);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_college_guide);
		
		// set up Tabs
		tabs = (TabHost)findViewById(R.id.tabhost);
		tabs.setup();
		
		TabHost.TabSpec spec;
		
		spec = tabs.newTabSpec("tab1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("MAP");
		tabs.addTab(spec);
		
		spec = tabs.newTabSpec("tab2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("Website");
		tabs.addTab(spec);
		
		spec = tabs.newTabSpec("tab3");
		spec.setContent(R.id.tab3);
		spec.setIndicator("spinner");
		tabs.addTab(spec);
		
		// set up WebView
		web = (WebView)findViewById(R.id.web);
		web.getSettings().setJavaScriptEnabled(true);

		// set up GoogleMap
		myMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		myMap.setMyLocationEnabled(true);
		myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(42.345017, -71.100380), zoom));
		
		// place the markers
		myMap.addMarker(new MarkerOptions()
		.position(Harvard)
		.title("Harvard University")
		.snippet("http://www.harvard.edu"));
		
		myMap.addMarker(new MarkerOptions()
		.position(MIT)
		.title("MIT")
		.snippet("http://web.mit.edu"));
		
		myMap.addMarker(new MarkerOptions()
		.position(BU)
		.title("Boston University")
		.snippet("http://www.bu.edu"));
		
		myMap.addMarker(new MarkerOptions()
		.position(Bentley)
		.title("Bentley University")
		.snippet("http://www.bentley.edu"));

		// set on click google map icon listener
		myMap.setOnMarkerClickListener(
				new OnMarkerClickListener() {
					public boolean onMarkerClick(Marker m) {
						String title = m.getTitle();
						website = m.getSnippet();
						Toast.makeText(getApplicationContext(), title, 
								Toast.LENGTH_LONG).show();
						tabs.setCurrentTab(1);
						web.loadUrl(website);
						
						// set the webview history inside this app
						web.setWebViewClient(new WebViewClient() {
							public boolean shouldOverrideUrlLoading(WebView view, String url) {
								view.loadUrl(url);
								return true;
							}
						});
						
						return true;
					}
				});
		
		// set up spinner and ArrayAdapter
		spinner = (Spinner)findViewById(R.id.spinner);
		adapter = ArrayAdapter.createFromResource(this, R.array.info, 
				android.R.layout.simple_spinner_dropdown_item);
		
		spinner.setAdapter(adapter);
		// set listener for spinner
		spinner.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
		
		
	}
	
	// set back key to go to previous web page
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
			web.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	// manage data through XML
	class SpinnerXMLSelectedListener implements OnItemSelectedListener{
		public void onItemSelected(AdapterView<?> arg0, 
				View arg1, int arg2, long arg3) {
			// read the website through different selection
			switch (arg0.getSelectedItemPosition()) {
			case 0:
				website = "http://www.restaurantweekboston.com/";
				tabs.setCurrentTab(1);
				web.loadUrl(website);
				break;
			case 1:
				website = "http://en.wikipedia.org/wiki/Copley_Square";
				tabs.setCurrentTab(1);
				web.loadUrl(website);
				break;
			case 2:
				website = "http://www.bpl.org/";
				tabs.setCurrentTab(1);
				web.loadUrl(website);
				break;
			case 3:
				website = "http://www.zara.com/us/";
				tabs.setCurrentTab(1);
				web.loadUrl(website);
				break;
			case 4:
				website = "https://www.hertz.com/rentacar/reservation/";
				tabs.setCurrentTab(1);
				web.loadUrl(website);
				break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
	}

}
