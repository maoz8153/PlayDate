//package com.playdate.usersetting;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.os.Environment;
//
//public class UserImages extends Activity
//{
//    
//    private Button b1; //button to save image
//    private String url;
//     
//  //path to the SD card, where we will save our image
//    private String PATH = Environment.getExternalStorageDirectory().toString();
//          
//    @Override
//       public void onCreate(Bundle savedInstanceState)
//       {
//           super.onCreate(savedInstanceState);
//           setContentView(R.layout.downloadactivity);
//            
//           b1=(Button)findViewById(R.id.button2);            
//           b1.setClickable(false);
//           new DownloadAndSaveTask().execute("http://karanbalkar.com/wp-content/uploads/2012/09/jellybean2.jpg");        
//       }
//     
//     
//       private class DownloadAndSaveTask extends AsyncTask<String, Void, Bitmap>
//        {    
//           private final ProgressDialog d=new ProgressDialog(DownloadAndSaveImage.this);  
//           @Override
//           protected void onPreExecute()
//           {
//             super.onPreExecute();
//             d.setMessage("Downloading image...Please wait!!");
//             d.setCancelable(false);
//             d.show();
//              
//          }
//
//          @Override
//          protected Bitmap doInBackground(String... params)
//          {
//              url = params[0]; 
//                try
//                   {           
//                    return BitmapFactory.decodeStream(new URL(url).openConnection().getInputStream());
//                   }
//                catch (MalformedURLException e)
//                   {
//                    e.printStackTrace();
//                    return null;
//                   }
//                catch (IOException e)
//                   {
//                    e.printStackTrace();
//                    return null;
//                   }
//                                        
//            }
//           
//           @Override
//           protected void onPostExecute(final Bitmap result)
//            {
//               super.onPostExecute(result);
//               if(result!=null)
//               {              
//               d.dismiss();
//                   b1.setClickable(true);
//                   b1.setOnClickListener(new OnClickListener()
//                   {
//                      @Override
//                          public void onClick(View v)
//                          {
//                              // TODO Auto-generated method stub
//                            OutputStream outStream = null;
//                          //sampleimage4 is the name given to the image saved. You can give any name
//                            File file = new File(PATH, "sampleimage4.jpg");
//                               try
//                                 {
//                                outStream = new FileOutputStream(file);
//                                //saving as a JPEG image
//                                result.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
//                                outStream.flush();
//                                outStream.close();  
//                                Toast.makeText(DownloadAndSaveImage.this, "Saved", Toast.LENGTH_LONG).show();
//                                  }   
//                                catch(FileNotFoundException e)
//                                 {
//                                //TODO Auto-generated catch block
//                                 e.printStackTrace();
//                                 }           
//                                catch(IOException e)
//                                {
//                               //TODO Auto-generated catch block
//                                e.printStackTrace();
//                                  }   
//                
//                              }           
//                        });           
//                   }       
//            } //end postexecute method
//    } //end asynctask class       
//} //end main class